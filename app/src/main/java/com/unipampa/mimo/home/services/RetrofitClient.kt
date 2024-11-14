package com.unipampa.mimo.home.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun build(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/") // Altere para o seu URL base
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}