package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    var name: String,   // Ogabek Matyakubov TBC
    val number: String,   // 9860 3501 0931 1224   // ID
    val expireDate: String,   // month/year - mm/yy
    var balance: Int = 0,  // 12 546 UZS
    val cardType: CardTypes,   // Humo, UzCard, Visa, Mastercard
    val history: ArrayList<History> = ArrayList(),   // Card's Transaction history
    var cardDesign: CardDesign,   // Card Design Types
    val ownerID: Long
)

enum class CardTypes {
    HUMO,
    UZCARD,
    VISA,
    OTHER
    // and more
}
enum class CardDesign {
    CARD_1,
    CARD_2,
    CARD_3,
    CARD_4
    // and more
}