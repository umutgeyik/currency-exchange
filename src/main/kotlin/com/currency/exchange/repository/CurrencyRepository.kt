package com.currency.exchange.repository

import com.currency.exchange.model.entity.Currency
import org.springframework.data.repository.CrudRepository

interface CurrencyRepository: CrudRepository<Currency, String> {}