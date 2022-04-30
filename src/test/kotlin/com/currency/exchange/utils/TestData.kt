package com.currency.exchange.utils

import com.currency.exchange.model.entity.ExchangeRate

class TestData {

    fun createExchangeRates(count: Int): ArrayList<ExchangeRate> {
        val list = arrayListOf<ExchangeRate>()

        for (i in 1..count) {
            list.add(
                ExchangeRate(
                    "2030-01-$i",
                    exchangeRate = i,
                    currencyName = "SEK",
                )
            )
        }

        return list
    }
}