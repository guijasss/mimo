package com.unipampa.mimo.home.entities

import com.google.firebase.Timestamp

data class Chat(
    val id: String = "",
    val sender: String? = null,
    val recipient: String? = null,
    val lastMessage: String = "",
    val createdAt: Timestamp = Timestamp.now()
)
