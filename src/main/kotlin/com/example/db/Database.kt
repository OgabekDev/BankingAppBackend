package com.example.db

import com.example.model.*
import com.example.utils.*

class Database: UserDB, CardDB, HistoryDB {

    private val users: HashMap<Long, User> = HashMap()
    private val cards: HashMap<String, Card> = HashMap()
    private val histories: HashMap<Long, History> = HashMap()

    override fun createUser(firstName: String, lastName: String, phoneNumber: String): ReturnType<User>  {

        if (!phoneNumber.isValidPhoneNumber())
            return ReturnType(Status.ERROR, errorMessage = "Not valid phone number")

        val user = User(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber
        )

        users[user.id] = user

        return ReturnType(Status.SUCCESS, body = user)

    }

    override fun getUser(userID: Long): ReturnType<User> {

        if (!users.containsKey(userID))
            return ReturnType(Status.ERROR, errorMessage = "User Not found")

        return ReturnType(Status.SUCCESS, body = users[userID])

    }

    override fun updateUser(
        userID: Long,
        firstName: String,
        lastName: String,
        phoneNumber: String
    ): ReturnType<Boolean> {

        if (phoneNumber.isValidPhoneNumber())
            return ReturnType(Status.ERROR, errorMessage = "Not valid phone number")

        users[userID]!!.firstName = firstName
        users[userID]!!.lastName = lastName
        users[userID]!!.phoneNumber = phoneNumber

        return ReturnType(Status.SUCCESS, body = true)

    }

    override fun deleteUser(userID: Long): ReturnType<Boolean> {

        if (users.containsKey(userID))
            return ReturnType(Status.ERROR, errorMessage = "User Not found")

        users.remove(userID)

        for (i in cards.values)
            if (i.ownerID == userID)
                cards.remove(i.number)

        return ReturnType(Status.SUCCESS, body = true)

    }

    override fun addCard(
        name: String,
        number: String,
        expireDate: String,
        ownerID: Long,
        cardDesign: CardDesign
    ): ReturnType<Card> {

        if (!number.isValidCardNumber())
            return ReturnType(Status.ERROR, errorMessage = "Not valid card number")

        if (!expireDate.isValidCardExpireDate())
            return ReturnType(Status.ERROR, errorMessage = "Not valid card expire date")

        val card = Card(
            name = name,
            number = number,
            expireDate = expireDate,
            balance = random(0, 100_000_000),
            cardType = cardType(number),
            history = ArrayList(),
            cardDesign = cardDesign,
            ownerID = ownerID
        )

        cards[card.number] = card
        users[ownerID]!!.cards.add(card)

        return ReturnType(Status.SUCCESS, body = card)

    }

    override fun getCard(cardId: String): ReturnType<Card> {

        if (!cardId.isValidCardNumber())
            return ReturnType(Status.ERROR, errorMessage = "Card Not found")

        if (!cards.containsKey(cardId))
            return ReturnType(Status.ERROR, errorMessage = "Card Not found")

        return ReturnType(Status.SUCCESS, body = cards[cardId])

    }

    override fun updateCard(cardId: String, cardName: String, cardDesign: CardDesign): ReturnType<Boolean> {

        if (!isCardHave(cardId))
            return ReturnType(Status.ERROR, errorMessage = "Card Not found")

        val cardIndex = users[cards[cardId]!!.ownerID]!!.cards.indexOf(cards[cardId])

        cards[cardId]!!.name = cardName
        cards[cardId]!!.cardDesign = cardDesign

        users[cards[cardId]!!.ownerID]!!.cards[cardIndex] = cards[cardId]!!

        return ReturnType(Status.SUCCESS, body = true)

    }

    override fun deleteCard(cardId: String): ReturnType<Boolean> {

        if (!isCardHave(cardId))
            return ReturnType(Status.ERROR, errorMessage = "Card Not found")

        users[cards[cardId]!!.ownerID]!!.cards.remove(cards[cardId])

        cards.remove(cardId)

        return ReturnType(Status.SUCCESS, body = true)

    }

    override fun isCardHave(cardId: String): Boolean {
        if (!cardId.isValidCardNumber())
            return false

        if (!cards.containsKey(cardId))
            return false

        return true
    }

    override fun transferP2P(senderID: String, receiverID: String, amount: Int): ReturnType<History> {

        if (!isCardHave(senderID))
            return ReturnType(Status.ERROR, errorMessage = "Sender Card Not found")

        if (!isCardHave(receiverID))
            return ReturnType(Status.ERROR, errorMessage = "Receiver Card Not found")

        val fee: Int = (amount * 0.01).toInt()
        val total = amount + fee

        if (cards[senderID]!!.balance < total)
            return ReturnType(Status.ERROR, errorMessage = "You don't have enough amount to send")

        val ownerId = cards[senderID]!!.ownerID
        val takerId = cards[receiverID]!!.ownerID
        val ownerCardIndex = users[ownerId]!!.cards.indexOf(cards[senderID])
        val takerCardIndex = users[takerId]!!.cards.indexOf(cards[receiverID])

        users[ownerId]!!.cards[ownerCardIndex].balance -= total
        users[takerId]!!.cards[takerCardIndex].balance += amount

        val history = History(
            type = PaymentType.P2P,
            sender = cards[senderID]!!.number.takeLast(4),
            senderId = ownerId,
            receiver = cards[receiverID]!!.number.takeLast(4),
            receiverId = takerId,
            price = amount,
            fee = fee,
            total = total
        )

        createHistory(history)

        return ReturnType(Status.SUCCESS, body = history)

    }

    override fun createHistory(history: History): ReturnType<History> {
        histories[history.id] = history

        return ReturnType(Status.SUCCESS, body = history)
    }

    override fun getHistory(historyID: Long): ReturnType<History> {
        if (!histories.containsKey(historyID))
            return ReturnType(Status.ERROR, errorMessage = "History Not found")

        return ReturnType(Status.SUCCESS, body = histories[historyID])
    }

    override fun getHistories(userID: Long): ReturnType<ArrayList<History>> {
        val result: ArrayList<History> = ArrayList()
        for (i in histories.values) {
            if (i.receiverId == userID || i.senderId == userID)
                result.add(i)

        }

        return ReturnType(Status.SUCCESS, body = result)
    }

}