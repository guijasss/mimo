package com.unipampa.mimo.home.helpers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Datetime {
    fun getCurrentDatetime(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()) // Formato de data e hora
        val date = Date() // Hora e data atual
        return dateFormat.format(date)
    }
}