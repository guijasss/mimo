package com.unipampa.mimo.home.helpers

import java.security.MessageDigest

object Hash {
    fun generateHash(strings: List<String>): String {
        val concatenatedString = strings.joinToString("") // Concatena todas as strings em uma única string
        val bytes = concatenatedString.toByteArray()
        val digest = MessageDigest.getInstance("SHA-256").digest(bytes)
        return digest.joinToString("") { "%02x".format(it) } // Converte os bytes para uma string hexadecimal
    }
}
