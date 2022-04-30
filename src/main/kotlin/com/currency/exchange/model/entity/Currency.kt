package com.currency.exchange.model.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
open class Currency (
    @Id
    open val currencyName: String = ""
)