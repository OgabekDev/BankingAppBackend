package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Login (
    val userID: Long,
    val password: String
)