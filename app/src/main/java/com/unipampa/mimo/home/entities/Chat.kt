package com.unipampa.mimo.home.entities

data class Chat(
    val id: String = "",
    val sender: String? = null,
    val recipient: String? = null,
    val lastMessage: String = "",
    val createdAt: String = ""
)
