package com.unipampa.mimo.home.entities

data class User (
    val name: String,
    val location: Location,
    val profilePicture: String,
    val username: String,
    val phoneNumbers: ArrayList<String>
)
