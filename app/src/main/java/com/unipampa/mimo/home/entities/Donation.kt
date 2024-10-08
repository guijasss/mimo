package com.unipampa.mimo.home.entities

data class Donation(
    val title: String,
    val description: String,
    val category: String,
    val requester: User
)
