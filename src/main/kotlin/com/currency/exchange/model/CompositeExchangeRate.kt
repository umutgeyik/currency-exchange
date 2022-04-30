package com.currency.exchange.model

import java.io.Serializable

data class CompositeExchangeRate (
    val date: String = "",
    val exchangeRate: Number = 0,
    val currencyName: String = ""
) : Serializable