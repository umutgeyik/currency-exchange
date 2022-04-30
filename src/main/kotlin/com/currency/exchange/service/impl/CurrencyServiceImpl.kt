package com.currency.exchange.service.impl

import com.currency.exchange.model.entity.Currency
import com.currency.exchange.repository.CurrencyRepository
import com.currency.exchange.service.CurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CurrencyServiceImpl: CurrencyService {

    @Autowired
    private lateinit var currencyRepository: CurrencyRepository

    override fun isCurrencyExist(currency: String): Boolean {
        return currencyRepository.existsById(currency)
    }

    override fun saveCurrency(currency: Currency) {
        currencyRepository.save(currency)
    }
}