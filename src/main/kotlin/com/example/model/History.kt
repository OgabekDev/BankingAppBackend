package com.example.model

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class History(
    val id: Long = System.currentTimeMillis(),   // ID
    val type: PaymentType,   // Payment Type
    val sender: String,   // Sender card   // **** **** **** 1224
    val senderId: Long,   // Sender ID
    val receiver: String,   // Receiver card   // **** **** **** 1224
    val receiverId: Long,   // Receiver ID
    val price: Int,   // Price 100 000
    val fee: Int,   // Fee 1%
    val total: Int, // Total 101 000
    val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"))   // Time 07/08/2023 03:20:00
)

enum class PaymentType {
    P2P
    // and more
}
