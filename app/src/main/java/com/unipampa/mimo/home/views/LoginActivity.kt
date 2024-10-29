package com.unipampa.mimo.home.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unipampa.mimo.R

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // Definindo o layout de login

        //Referencia os componentes que são do layout e direciona o botão de login
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        //Verificação se os campos estão vazios
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        //Login de teste - alterar lógica para o Firebase
        if (username == "admin" && password == "123") {
            // Armazenar estado de autenticação
            val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("isAuthenticated", true).apply()

            //Login sendo bem-sucedido, vai redirecionar para a MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finaliza a LoginActivity
        } else {
            Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
        }
    }
}
