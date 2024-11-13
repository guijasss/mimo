package com.unipampa.mimo.home.views

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.unipampa.mimo.R
import com.unipampa.mimo.home.adapters.MessageAdapter
import com.unipampa.mimo.home.entities.Message


class ChatActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var sendButton: ImageButton
    private lateinit var messageInput: EditText
    private lateinit var currentUserRef: String
    private lateinit var recipientUserRef: String
    private lateinit var donationRef: String

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Inicializando Firestore e referências
        firestore = FirebaseFirestore.getInstance()
        currentUserRef = intent.getParcelableExtra("sender", String::class.java)!! // Defina o ID do usuário atual
        recipientUserRef = intent.getParcelableExtra("recipient", String::class.java)!! // Recebe a referência do destinatário
        donationRef = intent.getParcelableExtra("donationId", String::class.java)!! // Recebe a referência da doação

        // Configurando o RecyclerView e o Adapter
        messagesRecyclerView = findViewById(R.id.recycler_view_messages)
        messageAdapter = MessageAdapter(currentUserRef)
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        messagesRecyclerView.adapter = messageAdapter

        // Configurando o botão de enviar e campo de entrada de mensagem
        sendButton = findViewById(R.id.button_send)
        messageInput = findViewById(R.id.input_message)

        // Carrega as mensagens existentes e fica escutando novas mensagens em tempo real
        loadMessages()

        // Envia uma nova mensagem quando o botão for clicado
        sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun loadMessages() {
        firestore.collection("message")
            .whereEqualTo("donation", donationRef)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@addSnapshotListener
                }

                val messages = snapshots?.toObjects(Message::class.java)
                if (messages != null) {
                    messageAdapter.submitList(messages) // Atualiza as mensagens no RecyclerView
                }
            }
    }

    private fun sendMessage() {
        val messageText = messageInput.text.toString().trim()
        if (messageText.isNotEmpty()) {
            val message = Message(
                message = messageText,
                sender = currentUserRef,
                recipient = recipientUserRef,
                timestamp = Timestamp.now(),
                donation = donationRef
            )

            // Adiciona a mensagem ao Firestore
            firestore.collection("message")
                .add(message)
                .addOnSuccessListener {
                    messageInput.text.clear() // Limpa o campo de entrada de texto
                }
                .addOnFailureListener { e ->
                    e.printStackTrace() // Lida com falhas no envio
                }
        }
    }
}
