package com.currency.exchange.repository

import com.currency.exchange.model.entity.Currency
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class CurrencyRepositoryTest @Autowired constructor(
    private val currencyRepository: CurrencyRepository
) {
    @Test
    fun `When save Currency should exist`() {
        currencyRepository.save(Currency("test-currency"))
        val found = currencyRepository.findById("test-currency")
        Assertions.assertTrue(found.isPresent)
    }

    @Test
    fun `When isExist Currency should exist`() {
        currencyRepository.save(Currency("test-currency"))
        val found = currencyRepository.existsById("test-currency")
        Assertions.assertTrue(found)
    }

    @Test
    fun `When isExist for non-existing Currency should fail`() {
        val found = currencyRepository.existsById("test-currency")
        Assertions.assertFalse(found)
    }
}