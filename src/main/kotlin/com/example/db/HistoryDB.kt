package com.example.db

import com.example.model.*

interface HistoryDB {

    fun createHistory(
        history: History
    ): ReturnType<History>

    fun getHistory(
        historyID: Long
    ): ReturnType<History>

    fun getHistories(
        userID: Long
    ): ReturnType<ArrayList<History>>
}
