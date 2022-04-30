package com.currency.exchange.service

import com.currency.exchange.model.entity.ExchangeRate
import com.currency.exchange.model.request.ExchangeRatesRequest
import com.currency.exchange.model.request.PostExchangeRequest
import com.currency.exchange.model.response.ExchangeRateResponse
import com.currency.exchange.model.response.LatestExchangeRateResponse

interface ExchangeRateService {
    fun getAllExchangeRatesByDate(date: String): Iterable<ExchangeRateResponse>
    fun getLatestExchangeRateByCurrency(currency: String): LatestExchangeRateResponse
    fun getExchangeRatesByDatePeriod(exchangeRequest: ExchangeRatesRequest): Iterable<ExchangeRateResponse>
    fun saveAllExchangeRates(currencies: List<ExchangeRate>)
    fun saveExchangeRate(exchangeRate: PostExchangeRequest): ExchangeRateResponse
}