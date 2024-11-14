package com.unipampa.mimo.home.interactors

import com.unipampa.mimo.home.HomeContracts
import com.unipampa.mimo.home.entities.Donation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DonationApiService {
    @GET("/donations")
    fun getDonations(): Call<List<Donation>>
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5000/"

    val apiService: DonationApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DonationApiService::class.java)
    }
}

class HomeInteractor : HomeContracts.Interactor {
    override fun downloadDonationsList(callback: HomeContracts.Interactor.InteractorCallback) {
        val apiService = RetrofitClient.apiService

        apiService.getDonations().enqueue(object : Callback<List<Donation>> {
            override fun onResponse(call: Call<List<Donation>>, response: Response<List<Donation>>) {
                if (response.isSuccessful) {
                    val donations = response.body() ?: ArrayList()
                    callback.onDonationsRetrieved(ArrayList(donations))
                } else {
                    callback.onDonationsRetrieved(ArrayList())
                }
            }

            override fun onFailure(call: Call<List<Donation>>, t: Throwable) {
                callback.onDonationsRetrieved(ArrayList())
            }
        })
    }
}
