package com.currency.exchange.service

import com.currency.exchange.model.entity.Currency
import com.currency.exchange.repository.CurrencyRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class CurrencyServiceTest @Autowired constructor(
    private val currencyService: CurrencyService,
    private val currencyRepository: CurrencyRepository
){
    @Test
    fun `When isCurrencyExist should return true`() {
        currencyRepository.save(Currency("test-currency"))
        val found = currencyService.isCurrencyExist("test-currency")
        Assertions.assertTrue(found)
    }

    @Test
    fun `When isCurrencyExist for non-existing currency should return false`() {
        val found = currencyService.isCurrencyExist("test-currency-not-exist")
        Assertions.assertFalse(found)
    }

    @Test
    fun `When saveCurrency should save currency`() {
        currencyService.saveCurrency(Currency("test-currency"))
        currencyRepository.existsById("test-currency").let {
            Assertions.assertTrue(it)
        }
    }
}