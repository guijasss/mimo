package com.unipampa.mimo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unipampa.mimo.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o View Binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o botão de cadastro
        binding.btnCadastrar.setOnClickListener {
            val nome = binding.etNome.text.toString()
            val email = binding.etEmail.text.toString()
            val senha = binding.etSenha.text.toString()
            /*val cidade = binding.etCidade.text.toString()
            val endereco = binding.etEndereco.text.toString()*/

            // Lógica de validação simples
            if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                // Aqui você pode adicionar a lógica para salvar os dados
                Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                finish() // Fecha a Activity após o cadastro
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}