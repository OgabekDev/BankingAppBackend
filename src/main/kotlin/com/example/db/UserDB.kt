package com.example.db

import com.example.model.*

interface UserDB {

    fun createUser(
        firstName: String,
        lastName: String,
        phoneNumber: String
    ): ReturnType<User>

    fun getUser(
        userID: Long
    ): ReturnType<User>

    fun updateUser(
        userID: Long,
        firstName: String,
        lastName: String,
        phoneNumber: String
    ): ReturnType<Boolean>

    fun deleteUser(
        userID: Long
    ): ReturnType<Boolean>
}
