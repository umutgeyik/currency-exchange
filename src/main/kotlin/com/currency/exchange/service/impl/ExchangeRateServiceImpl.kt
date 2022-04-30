package com.currency.exchange.service.impl

import com.currency.exchange.model.entity.ExchangeRate
import com.currency.exchange.model.request.ExchangeRatesRequest
import com.currency.exchange.model.request.PostExchangeRequest
import com.currency.exchange.model.response.ExchangeRateResponse
import com.currency.exchange.model.response.LatestExchangeRateResponse
import com.currency.exchange.repository.ExchangeRateRepository
import com.currency.exchange.service.CurrencyService
import com.currency.exchange.service.ExchangeRateService
import com.currency.exchange.utils.BadFormatException
import com.currency.exchange.utils.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@Service
class ExchangeRateServiceImpl: ExchangeRateService {
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val exchangeRateFormatter = DecimalFormat("#.##")

    @Autowired
    private lateinit var exchangeRateRepository: ExchangeRateRepository

    @Autowired
    private lateinit var currencyService: CurrencyService

    override fun getAllExchangeRatesByDate(date: String): Iterable<ExchangeRateResponse> {
        try {
            validateDates(listOf(date))
            exchangeRateRepository.findExchangeRatesByDate(date).let { exchangeRates ->
                return exchangeRates.map { ExchangeRateResponse(it.date, it.exchangeRate, it.currencyName)}
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getLatestExchangeRateByCurrency(currency: String): LatestExchangeRateResponse {
        when(val result = exchangeRateRepository.findLatestByCurrency(currency)) {
            is ExchangeRate -> {
                return LatestExchangeRateResponse(date = result.date, exchangeRate = result.exchangeRate)
            } else -> {
                throw NotFoundException("Currency is not found")
            }
        }
    }

    override fun getExchangeRatesByDatePeriod(exchangeRequest: ExchangeRatesRequest): Iterable<ExchangeRateResponse> {
        currencyService.isCurrencyExist(exchangeRequest.currencyName).let { isCurrencyExist ->
            if(isCurrencyExist) {
                validateDates(listOf(exchangeRequest.fromDate,exchangeRequest.toDate))
                exchangeRateRepository.findExchangeRatesByDatePeriod(
                    exchangeRequest.currencyName,
                    exchangeRequest.fromDate,
                    exchangeRequest.toDate
                ).let { exchangeRates ->
                    return exchangeRates.map { ExchangeRateResponse(it.date, it.exchangeRate, it.currencyName)}
                }
            } else {
                throw NotFoundException("Currency is not found")
            }
        }
    }

    override fun saveAllExchangeRates(currencies: List<ExchangeRate>) {
        exchangeRateRepository.saveAll(currencies)
    }

    override fun saveExchangeRate(exchangeRate: PostExchangeRequest): ExchangeRateResponse {
        currencyService.isCurrencyExist(exchangeRate.currencyName).let { isCurrencyExist ->
            if(isCurrencyExist) {
                validateDates(listOf(exchangeRate.date))
                if(validateDates(listOf(exchangeRate.date)) && validateRate(exchangeRate.exchangeRate)) {
                    exchangeRateRepository.save(
                        ExchangeRate(
                            exchangeRate = exchangeRate.exchangeRate,
                            date = exchangeRate.date,
                            currencyName = exchangeRate.currencyName
                        )
                    ).let {
                        return ExchangeRateResponse(
                            exchangeRate = it.exchangeRate,
                            date = it.date,
                            currencyName = it.currencyName
                        )
                    }
                } else {
                    throw BadFormatException("Please check the request")
                }
            } else {
                throw NotFoundException("Currency is not found")
            }
        }
    }

    private fun validateDates(dates: List<String>): Boolean {
        dates.forEach {
            try {
                dateFormatter.parse(it)
            } catch (e: Exception) {
                throw BadFormatException("Date format should be yyyy-mm-dd")
            }
        }
        return true
    }

    private fun validateRate(rate: Number): Boolean {
        return try {
            exchangeRateFormatter.parse(rate.toString())
            true
        } catch (e: Exception) {
            false
        }
    }
}