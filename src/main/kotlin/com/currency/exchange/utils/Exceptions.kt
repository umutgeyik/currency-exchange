package com.currency.exchange.utils

class NotFoundException(override val message: String?): Exception(message)

class BadFormatException(override val message: String?): Exception(message)