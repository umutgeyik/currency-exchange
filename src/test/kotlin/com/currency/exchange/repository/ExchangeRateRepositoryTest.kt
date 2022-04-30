package com.currency.exchange.repository

import com.currency.exchange.model.entity.ExchangeRate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ExchangeRateRepositoryTest @Autowired constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {

    @Test
    fun `When save ExchangeRate should exist`() {
        exchangeRateRepository.save(ExchangeRate("2023-01-01",0,"test-currency"))
        exchangeRateRepository.findExchangeRatesByDate("2023-01-01").first().let {
            Assertions.assertEquals(it.exchangeRate, 0)
            Assertions.assertEquals(it.date, "2023-01-01")
            Assertions.assertEquals(it.currencyName, "test-currency")
        }
    }

    @Test
    fun `When findLatestByCurrency should give latest ExchangeRate`() {
        exchangeRateRepository.save(ExchangeRate("2030-01-01",0,"test-currency"))
        exchangeRateRepository.save(ExchangeRate("2040-01-01",0,"test-currency"))
        exchangeRateRepository.findLatestByCurrency("test-currency").let {
            Assertions.assertNotNull(it)
            Assertions.assertEquals(it!!.exchangeRate, 0)
            Assertions.assertEquals(it.date, "2040-01-01")
            Assertions.assertEquals(it.currencyName, "test-currency")
        }
    }

    @Test
    fun `When findExchangeRatesByDatePeriod should give ExchangeRates for given period`() {
        exchangeRateRepository.save(ExchangeRate("2030-01-01",0,"test-currency"))
        exchangeRateRepository.save(ExchangeRate("2040-01-01",0,"test-currency"))
        exchangeRateRepository.save(ExchangeRate("2050-01-01",0,"test-currency"))
        exchangeRateRepository.findExchangeRatesByDatePeriod("test-currency", "2035-01-01", "2045-01-01").let {
            Assertions.assertNotNull(it)
            Assertions.assertEquals(it.count(), 1)
            Assertions.assertEquals(it.first().exchangeRate, 0)
            Assertions.assertEquals(it.first().date, "2040-01-01")
            Assertions.assertEquals(it.first().currencyName, "test-currency")
        }
    }

    @Test
    fun `When findExchangeRatesByDate should give ExchangeRates for given date`() {
        exchangeRateRepository.save(ExchangeRate("2050-05-05",0,"test-currency"))
        exchangeRateRepository.save(ExchangeRate("2050-05-05",100,"test-currency-2"))
        exchangeRateRepository.findExchangeRatesByDate( "2050-05-05").let {
            Assertions.assertNotNull(it)
            Assertions.assertEquals(it.count(), 2)
        }
    }

    @Test
    fun `When findExchangeRatesByDate for non-existing date should be null`() {
        exchangeRateRepository.findExchangeRatesByDate( "2050-05-05").let {
            Assertions.assertEquals(it, arrayListOf<ExchangeRate>())
        }
    }
}