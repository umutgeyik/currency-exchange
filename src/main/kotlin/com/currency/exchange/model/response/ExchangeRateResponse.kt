package com.currency.exchange.model.response

data class ExchangeRateResponse (
    val date: String,
    val exchangeRate: Number,
    val currencyName: String
)