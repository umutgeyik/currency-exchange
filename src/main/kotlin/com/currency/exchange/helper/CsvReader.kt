package com.currency.exchange.helper

import com.currency.exchange.model.entity.Currency
import com.currency.exchange.model.entity.ExchangeRate
import com.currency.exchange.service.CurrencyService
import com.currency.exchange.service.ExchangeRateService
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@Component
class CsvReader {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val CSV_File_Path = "/data/*.csv"
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val exchangeRateFormatter = DecimalFormat("#.##")

    @Autowired
    lateinit var exchangeRateService: ExchangeRateService

    @Autowired
    lateinit var currencyService: CurrencyService

    @EventListener(ApplicationReadyEvent::class)
    fun readCsv() {

        val resources = PathMatchingResourcePatternResolver().getResources(CSV_File_Path)
        resources.forEach {
            readFromCSV(it.inputStream)
        }
        logger.info("All csv files are persisted to the database")
    }

    fun readFromCSV(inputStream: InputStream) {
        val csvParser = CSVParser(BufferedReader(InputStreamReader(inputStream)), CSVFormat.DEFAULT)
        val csvRecords = csvParser.records

        val currencyList = arrayListOf<ExchangeRate>()
        val currencyName = validateCurrency(csvRecords[0])

        if(!currencyName.isNullOrBlank()) {
            for (csvRecord in csvRecords.drop(1)) {
                validateCsvRecord(csvRecord).let {
                    if(it != null) {
                        currencyList.add(
                            ExchangeRate(
                                date = it.date,
                                exchangeRate = it.exchangeRate,
                                currencyName = currencyName
                            )
                        )
                    }
                }
            }
            currencyService.saveCurrency(Currency(currencyName))
            exchangeRateService.saveAllExchangeRates(currencyList)
            logger.info("$currencyName currency file is processed")
        }
    }

    private fun validateCsvRecord(csvRecord: CSVRecord): ExchangeRateRecord? {
        if(csvRecord.size() != 2) return null

        val date = csvRecord.get(0)
        val value = csvRecord.get(1)
        return try {
            dateFormatter.parse(date)
            val parsedValue = exchangeRateFormatter.parse(value)
            ExchangeRateRecord(date = date, exchangeRate = parsedValue)
        } catch (e: Exception) {
            null
        }
    }

    private fun validateCurrency(currencyRecord: CSVRecord): String? {
        if(currencyRecord.size() != 2) return null

        val currencyColumnArray = currencyRecord.get(1).toString().split("USD")
        if(currencyColumnArray.size != 2 && currencyColumnArray[1].isNotBlank()) return null

        return currencyColumnArray[0]
    }
}