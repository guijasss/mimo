package com.unipampa.mimo.home.views

import java.util.UUID
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.unipampa.mimo.R
import com.unipampa.mimo.home.entities.Donation

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

            val donation = Donation(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                category = category
            )

            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("donation")
                .add(donation)
                .addOnSuccessListener {
                    Toast.makeText(this, "Doação criada com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Fecha a activity após salvar
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao criar doação: ${e.message}", Toast.LENGTH_SHORT).show()
                }

            val resultIntent = Intent()
            resultIntent.putExtra(MainActivity.DONATION_EXTRA, donation)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
