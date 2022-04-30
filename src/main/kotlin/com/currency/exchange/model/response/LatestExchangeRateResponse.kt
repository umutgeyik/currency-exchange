package com.currency.exchange.model.response

data class LatestExchangeRateResponse (
    val date: String,
    val exchangeRate: Number
    )