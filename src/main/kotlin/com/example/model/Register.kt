package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Register (
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val password: String
)