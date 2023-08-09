package com.example.utils

import com.example.model.CardTypes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun String.isValidPhoneNumber(): Boolean {
    val regex = """^\+\d{3}\s\d{2}\s\d{3}\s\d{2}\s\d{2}$""".toRegex()
    return regex.matches(this)
}

fun String.isValidCardNumber(): Boolean {
    val regex = """^\d{4}\s\d{4}\s\d{4}\s\d{4}$""".toRegex()
    return regex.matches(this)
}

fun String.isValidCardExpireDate(): Boolean {
    val expirationDateRegex = """^(0[1-9]|1[0-2])/\d{2}$""".toRegex()
    return expirationDateRegex.matches(this)
//    if (!expirationDateRegex.matches(this))
//        return false
//
//    val currentYear = LocalDate.now().year % 100 // Get the last two digits of the current year
//    val currentMonth = LocalDate.now().monthValue
//
//    val dateFormatter = DateTimeFormatter.ofPattern("MM/yy")
//    val expirationDateParsed = LocalDate.parse("01/$this", dateFormatter)
//    val expirationYear = expirationDateParsed.year % 100
//    val expirationMonth = expirationDateParsed.monthValue
//
//    return expirationYear >= currentYear && (expirationYear > currentYear || expirationMonth > currentMonth)
}

fun cardType(cardNumber: String): CardTypes {
    return when {
        cardNumber.startsWith("9860") -> CardTypes.HUMO
        cardNumber.startsWith("8600") -> CardTypes.UZCARD
        cardNumber.startsWith("4147") -> CardTypes.VISA
        else -> CardTypes.OTHER
    }
}

fun random(from: Int, to: Int): Int {
    val random = Random.Default
    return from + random.nextInt() * (to - from)
}