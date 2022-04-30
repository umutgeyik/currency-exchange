package com.currency.exchange.model.entity

import com.currency.exchange.model.CompositeExchangeRate
import javax.persistence.*
@Entity
@IdClass(CompositeExchangeRate::class)
open class ExchangeRate(
    @Id
    open val date: String = "",
    @Id
    open val exchangeRate: Number = 0,
    @Id
    open val currencyName: String = ""
)