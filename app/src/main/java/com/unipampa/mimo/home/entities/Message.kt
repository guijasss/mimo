package com.unipampa.mimo.home.entities

import com.google.firebase.Timestamp

data class Message(
    val message: String = "",
    val recipient: String = "",
    val sender: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val chatId: String = "",
    val donationId: String
)
