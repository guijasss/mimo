package com.unipampa.mimo.home.views

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unipampa.mimo.R
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.entities.Location
import com.unipampa.mimo.home.entities.User

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

            // Aqui você pode adicionar a lógica de criação da doação
            val donation = Donation(
                id = 0, // O ID pode ser gerado automaticamente
                title = title,
                description = description,
                category = category,
                requester = User(
                    name = "Nome do Usuário",
                    location = Location(city = "Cidade", state = "Estado"),
                    profilePicture = "path/to/profile/pic",
                    username = "username",
                    phoneNumbers = arrayListOf("123456789")
                )
            )

            // Adicione a lógica para salvar ou enviar a doação
            Toast.makeText(this, "Doação criada: ${donation.title}", Toast.LENGTH_SHORT).show()
        }
    }
}
