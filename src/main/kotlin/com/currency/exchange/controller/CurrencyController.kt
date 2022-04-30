package com.currency.exchange.controller

import com.currency.exchange.model.request.ExchangeRatesRequest
import com.currency.exchange.model.request.PostExchangeRequest
import com.currency.exchange.model.response.ExchangeRateResponse
import com.currency.exchange.model.response.LatestExchangeRateResponse
import com.currency.exchange.service.ExchangeRateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/currency")
class CurrencyController {

    @Autowired
    lateinit var exchangeRateService: ExchangeRateService

    @GetMapping
    fun getLatestExchangeRateByCurrency(@RequestParam currency: String):
            LatestExchangeRateResponse = exchangeRateService.getLatestExchangeRateByCurrency(currency)

    @GetMapping("/period")
    fun getExchangeRatesByDatePeriod(@Valid exchangeRequest: ExchangeRatesRequest):
            Iterable<ExchangeRateResponse> = exchangeRateService.getExchangeRatesByDatePeriod(exchangeRequest)

    @GetMapping("/date")
    fun getExchangeRatesByDate(@RequestParam date: String):
            Iterable<ExchangeRateResponse> = exchangeRateService.getAllExchangeRatesByDate(date)

    @PostMapping
    fun postExchangeRate(@Valid @RequestBody exchangeRate: PostExchangeRequest):
            ExchangeRateResponse = exchangeRateService.saveExchangeRate(exchangeRate)
}