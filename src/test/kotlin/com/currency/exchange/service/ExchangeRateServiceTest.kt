package com.currency.exchange.service

import com.currency.exchange.model.entity.ExchangeRate
import com.currency.exchange.model.request.ExchangeRatesRequest
import com.currency.exchange.model.request.PostExchangeRequest
import com.currency.exchange.repository.ExchangeRateRepository
import com.currency.exchange.utils.BadFormatException
import com.currency.exchange.utils.NotFoundException
import com.currency.exchange.utils.TestData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class ExchangeRateServiceTest @Autowired constructor(
    private val exchangeRateService: ExchangeRateService,
    private val exchangeRateRepository: ExchangeRateRepository
){
    @Test
    fun `When getAllExchangeRatesByDate should return list of exchange rates`() {
        val testData = TestData().createExchangeRates(3)
        testData.forEach {
            exchangeRateRepository.save(it)
        }
        testData.forEach {
            val result = exchangeRateService.getAllExchangeRatesByDate(it.date)
            Assertions.assertEquals(result.count(), 1)
            Assertions.assertEquals(result.first().date, it.date)
            Assertions.assertEquals(result.first().exchangeRate, it.exchangeRate)
            Assertions.assertEquals(result.first().currencyName, it.currencyName)
        }
    }

    @Test
    fun `When getAllExchangeRatesByDate for non-existing exchange rates should return empty list`() {
        val testData = TestData().createExchangeRates(1)
        testData.first().let {
            val result = exchangeRateService.getAllExchangeRatesByDate(it.date)
            Assertions.assertEquals(result.count(), 0)
        }
    }

    @Test
    fun `When getAllExchangeRatesByDate with wrong date format should throw BadFormatException`() {
        val testData = TestData().createExchangeRates(1)
        testData.first().let {
            try{
                exchangeRateService.getAllExchangeRatesByDate("invalid-date")
            } catch (e:Exception) {
                Assertions.assertEquals(e.javaClass, BadFormatException("Date format should be yyyy-mm-dd").javaClass)
                Assertions.assertEquals(e.message, BadFormatException("Date format should be yyyy-mm-dd").message)
            }
        }
    }

    @Test
    fun `When getLatestExchangeRateByCurrency should return latest exchange rate`() {
        val testDataFirst = ExchangeRate(
            "2030-01-01",
            exchangeRate = 0,
            currencyName = "SEK",
        )

        val testDataSecond = ExchangeRate(
            "2030-01-02",
            exchangeRate = 10,
            currencyName = "SEK",
        )

        exchangeRateRepository.save(testDataFirst)
        exchangeRateRepository.save(testDataSecond)

        exchangeRateService.getLatestExchangeRateByCurrency("SEK").let {
            Assertions.assertEquals(it.date, testDataSecond.date)
            Assertions.assertEquals(it.exchangeRate, testDataSecond.exchangeRate)
        }
    }

    @Test
    fun `When getLatestExchangeRateByCurrency for non-existing currency should throw NotFoundException`() {
        try{
            exchangeRateService.getLatestExchangeRateByCurrency("invalid-currency")
        } catch (e:Exception) {
            Assertions.assertEquals(e.javaClass, NotFoundException("Currency is not found"). javaClass)
            Assertions.assertEquals(e.message, NotFoundException("Currency is not found").message)
        }
    }

    @Test
    fun `When getExchangeRatesByDatePeriod should return list of ExchangeRates`() {
        val testData = TestData().createExchangeRates(3)
        testData.forEach {
            exchangeRateRepository.save(it)
        }
        exchangeRateService.getExchangeRatesByDatePeriod(ExchangeRatesRequest(
            currencyName = "SEK",
            fromDate = "2029-01-01",
            toDate = "2031-01-01"
        )).let {
            Assertions.assertEquals(it.count(), 3)
            it.forEach { exchangeRate ->
                Assertions.assertEquals(exchangeRate.currencyName, "SEK")
            }
        }
    }

    @Test
    fun `When getExchangeRatesByDatePeriod for non-existing exchange rates should return null`() {
        exchangeRateService.getExchangeRatesByDatePeriod(ExchangeRatesRequest(
            currencyName = "SEK",
            fromDate = "2040-01-01",
            toDate = "2041-01-01"
        )).let {
            Assertions.assertEquals(it.count(), 0)
        }
    }

    @Test
    fun `When getExchangeRatesByDatePeriod for invalid dates should throw BadFormatException`() {
        try{
            exchangeRateService.getExchangeRatesByDatePeriod(ExchangeRatesRequest(
                currencyName = "SEK",
                fromDate = "invalid-date",
                toDate = "invalid-date"
            ))
        } catch (e:Exception) {
            Assertions.assertEquals(e.javaClass, BadFormatException("Date format should be yyyy-mm-dd").javaClass)
            Assertions.assertEquals(e.message, BadFormatException("Date format should be yyyy-mm-dd").message)
        }
    }

    @Test
    fun `When getExchangeRatesByDatePeriod for non-existing Currency should throw NotFoundException`() {
        try{
            exchangeRateService.getExchangeRatesByDatePeriod(ExchangeRatesRequest(
                currencyName = "invalid-currency",
                fromDate = "2040-01-01",
                toDate = "2041-01-01"
            ))
        } catch (e:Exception) {
            Assertions.assertEquals(e.javaClass, NotFoundException("Currency is not found"). javaClass)
            Assertions.assertEquals(e.message, NotFoundException("Currency is not found").message)
        }
    }

    @Test
    fun `When saveExchangeRate should save the ExchangeRate`() {
        val testData = TestData().createExchangeRates(1)
        val testDataValue = testData.first()
        exchangeRateService.saveExchangeRate(
            PostExchangeRequest(
            currencyName = testDataValue.currencyName,
                date = testDataValue.date,
                exchangeRate = testDataValue.exchangeRate
        )
        ).let {
            Assertions.assertEquals(it.exchangeRate, testDataValue.exchangeRate)
            Assertions.assertEquals(it.date, testDataValue.date)
            Assertions.assertEquals(it.currencyName, testDataValue.currencyName)
        }
    }

    @Test
    fun `When saveExchangeRate with non-existing Currency should throw NotFoundException`() {
        try{
            exchangeRateService.saveExchangeRate(
                PostExchangeRequest(
                    currencyName = "invalid-currency",
                    date = "2040-01-01",
                    exchangeRate = 0
                )
            )
        } catch (e:Exception) {
            Assertions.assertEquals(e.javaClass, NotFoundException("Currency is not found"). javaClass)
            Assertions.assertEquals(e.message, NotFoundException("Currency is not found").message)
        }
    }
}