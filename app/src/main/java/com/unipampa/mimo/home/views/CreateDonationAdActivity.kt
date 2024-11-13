package com.unipampa.mimo.home.views

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.unipampa.mimo.R
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.UUID

interface UserApi {
    @GET("users") // O endpoint agora é só "users", o parâmetro será passado via query
    fun getUserByUsername(@Query("username") username: String): Call<User>
}


class CreateDonationAdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_donation_ad)

        val titleEditText = findViewById<EditText>(R.id.donation_title)
        val descriptionEditText = findViewById<EditText>(R.id.donation_description)
        val categorySpinner = findViewById<Spinner>(R.id.donation_category)
        val createButton = findViewById<Button>(R.id.create_donation_button)

        createButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val category = categorySpinner.selectedItem.toString()

            val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "default_name") // Recupera o nome do usuário

            // Inicializa o Retrofit para fazer a requisição HTTP
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/") // Altere para o seu URL base
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val userApi = retrofit.create(UserApi::class.java)

            if (username != null) {
                userApi.getUserByUsername(username).enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val user = response.body()

                            if (user != null) {
                                val donation = Donation(
                                    id = UUID.randomUUID().toString(),
                                    title = title,
                                    description = description,
                                    category = category,
                                    creatorId = user.id,
                                    creator = user
                                )

                                val firestore = FirebaseFirestore.getInstance()
                                firestore.collection("donation")
                                    .add(mapOf(
                                        "id" to donation.id,
                                        "title" to donation.title,
                                        "description" to donation.description,
                                        "category" to donation.category,
                                        "creatorId" to "/user/${user.id}"
                                    ))
                                    .addOnSuccessListener {
                                        Toast.makeText(this@CreateDonationAdActivity, "Doação criada com sucesso!", Toast.LENGTH_SHORT).show()
                                        finish() // Fecha a activity após salvar
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this@CreateDonationAdActivity, "Erro ao criar doação: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this@CreateDonationAdActivity, "Erro ao buscar usuário", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@CreateDonationAdActivity, "Erro ao conectar com o servidor", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}
