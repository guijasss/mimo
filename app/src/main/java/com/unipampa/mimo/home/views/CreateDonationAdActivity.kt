package com.unipampa.mimo.home.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unipampa.mimo.R
import com.unipampa.mimo.home.entities.Donation
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

            val donation = Donation(
                id = 0,
                title = title,
                description = description,
                category = category
            )

            val resultIntent = Intent()
            resultIntent.putExtra(MainActivity.DONATION_EXTRA, donation)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
