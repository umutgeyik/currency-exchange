package com.currency.exchange.model.request

import com.currency.exchange.utils.BadFormatException

data class ExchangeRatesRequest (
    val currencyName: String,
    val fromDate: String,
    val toDate: String
    )
{
    init {
        if(currencyName.isBlank()) throw BadFormatException("Please check the request body")
        if(fromDate.isBlank()) throw BadFormatException("Please check the request body")
        if(fromDate.isBlank()) throw BadFormatException("Please check the request body")
    }
}