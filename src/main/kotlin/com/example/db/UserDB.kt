package com.example.db

import com.example.model.*

interface UserDB {

    fun createUser(
        id: Long,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String
    ): ReturnType<User>

    fun getUser(
        userID: Long
    ): ReturnType<User>

    fun updateUser(
        userID: Long,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String
    ): ReturnType<Boolean>

    fun deleteUser(
        userID: Long
    ): ReturnType<Boolean>

    fun changePassword(
        userID: Long,
        password: String
    ): ReturnType<Boolean>
}
