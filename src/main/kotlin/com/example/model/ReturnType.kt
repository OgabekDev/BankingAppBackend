package com.example.model

data class  ReturnType<T>(
    val status: Status,
    val body: T? = null,
    var errorMessage: String? = null
)

enum class Status {
    SUCCESS,
    ERROR
}