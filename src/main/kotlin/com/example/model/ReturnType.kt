package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class  ReturnType<T>(
    val status: Status,
    val body: T? = null,
    var errorMessage: String? = null
)

@Serializable
enum class Status {
    SUCCESS,
    ERROR
}