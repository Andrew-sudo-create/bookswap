package com.example.bookswap.models

import java.io.Serializable
import java.util.Locale

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val edition: String,
    val isbn: String,
    val price: Double,
    val condition: String,
    val category: String,
    val description: String,
    val sellerName: String,
    val sellerEmail: String
) : Serializable {
    val formattedPrice: String get() = String.format(Locale.US, "$%.2f", price)
    val authorAndEdition: String get() = "$author • $edition"
}