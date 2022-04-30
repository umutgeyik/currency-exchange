package com.currency.exchange.model.request

data class PostExchangeRequest (
    val currencyName: String,
    val date: String,
    val exchangeRate: Number
    )