package com.currency.exchange.repository

import com.currency.exchange.model.entity.ExchangeRate
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExchangeRateRepository: CrudRepository<ExchangeRate, String> {
    @Query(value = "select *\n" +
            "from public.exchange_rate t\n" +
            "inner join (\n" +
            "    select currency_name, max(date) as MaxDate\n" +
            "    from public.exchange_rate\n" +
            "    group by currency_name\n" +
            ") tm on t.currency_name = tm.currency_name and t.date = maxdate and t.currency_name = :currency", nativeQuery = true)
    fun findLatestByCurrency(currency: String): ExchangeRate?

    @Query(value = "select * from public.exchange_rate t " +
            "where date between :fromDate and :toDate and t.currency_name = :currencyName " +
            "ORDER BY date DESC", nativeQuery = true)
    fun findExchangeRatesByDatePeriod(currencyName: String, fromDate: String, toDate:String): Iterable<ExchangeRate>

    @Query(value = "select * from public.exchange_rate t " +
            "where t.date = :date", nativeQuery = true)
    fun findExchangeRatesByDate(date: String): Iterable<ExchangeRate>
}