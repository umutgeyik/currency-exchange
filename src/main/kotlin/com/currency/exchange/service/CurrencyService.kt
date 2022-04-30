package com.currency.exchange.service

import com.currency.exchange.model.entity.Currency

interface CurrencyService {
    fun isCurrencyExist(currency: String): Boolean
    fun saveCurrency(currency: Currency)
}