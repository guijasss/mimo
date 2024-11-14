package com.unipampa.mimo.home.views

import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.unipampa.mimo.databinding.ActivityRegisterBinding
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o Firebase e Firestore
        FirebaseApp.initializeApp(this)
        firestore = FirebaseFirestore.getInstance()

        // Inicializa o View Binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o botão de cadastro
        binding.btnCadastrar.setOnClickListener {
            val nome = binding.etNome.text.toString()
            val email = binding.etEmail.text.toString()
            val senha = binding.etSenha.text.toString()

            // Lógica de validação simples
            if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                val hashedPassword = hashPassword(senha) // Cria o hash SHA-1 da senha
                saveUserToFirestore(nome, email, hashedPassword)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-1")
        val hashedBytes = messageDigest.digest(password.toByteArray())
        return Base64.encodeToString(hashedBytes, Base64.DEFAULT).trim()
    }

    private fun saveUserToFirestore(nome: String, email: String, hashedPassword: String) {
        // Cria um novo objeto usuário com os dados
        val user = hashMapOf(
            "name" to nome,
            "username" to email,
            "password" to hashedPassword,
            "city" to "Alegrete",
            "address" to "Rua B 44"
        )

        // Adiciona o novo usuário ao Firestore na coleção "users"
        firestore.collection("user")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                finish() // Fecha a Activity após o cadastro
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao cadastrar usuário: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
