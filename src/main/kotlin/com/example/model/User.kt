package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long = System.currentTimeMillis(),   // ID
    var firstName: String,   // Ogabek
    var lastName: String,   // Matyakubov
    var phoneNumber: String,   // +998 93 203 73 13
    val cards: ArrayList<Card> = ArrayList(),   // User's Cards List
    val history: ArrayList<History> = ArrayList()   // User's Transaction List
)