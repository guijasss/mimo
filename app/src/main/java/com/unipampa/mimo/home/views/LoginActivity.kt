package com.unipampa.mimo.home.views

import android.util.Base64
import java.security.MessageDigest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unipampa.mimo.R

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var firestore: FirebaseFirestore
    private lateinit var registerPrompt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // Definindo o layout de login

        firestore = Firebase.firestore

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            login(username, password)
        }

        registerPrompt = findViewById(R.id.register_prompt)
        registerPrompt.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent) // Inicia a RegisterActivity
        }
    }

    private fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val hashedPassword = hashPassword(password)

        firestore.collection("user")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val currentUserId = document.id
                    val name: String = document.getString("name").toString()

                    val storedPassword = document.getString("password")
                    if (storedPassword == hashedPassword) {
                        saveAuthState(currentUserId, username, name)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao acessar Firestore: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveAuthState(id: String, username: String, name: String) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isAuthenticated", true)
        editor.putString("currentUserId", id)
        editor.putString("username", username)
        editor.putString("name", name)
        editor.apply()
    }

    private fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-1")
        val hashedBytes = messageDigest.digest(password.toByteArray())
        return Base64.encodeToString(hashedBytes, Base64.DEFAULT).trim()
    }
}
