package com.example.db

import com.example.model.Card
import com.example.model.CardDesign
import com.example.model.History
import com.example.model.ReturnType

interface CardDB {

    fun addCard(
        name: String,
        number: String,
        expireDate: String,
        ownerID: Long,
        cardDesign: CardDesign
    ): ReturnType<Card>

    fun getCard(
        cardId: String
    ): ReturnType<Card>

    fun updateCard(
        cardId: String,
        cardName: String,
        cardDesign: CardDesign
    ): ReturnType<Boolean>

    fun deleteCard(
        cardId: String
    ): ReturnType<Boolean>

    fun isCardHave(cardId: String): Boolean

    fun transferP2P(
        senderID: String,
        receiverID: String,
        amount: Int
    ): ReturnType<History>

}