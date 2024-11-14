package com.unipampa.mimo.home.views

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.unipampa.mimo.R
import com.unipampa.mimo.home.adapters.MessageAdapter
import com.unipampa.mimo.home.entities.Chat
import com.unipampa.mimo.home.entities.Message
import com.unipampa.mimo.home.helpers.Datetime
import com.unipampa.mimo.home.helpers.Hash


class ChatActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var sendButton: ImageButton
    private lateinit var messageInput: EditText
    private lateinit var sender: String
    private lateinit var recipient: String
    private lateinit var donationId: String

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Inicializando Firestore e referências
        firestore = FirebaseFirestore.getInstance()
        sender = intent.getParcelableExtra("sender", String::class.java)!! // Defina o ID do usuário atual
        recipient = intent.getParcelableExtra("recipient", String::class.java)!! // Recebe a referência do destinatário
        donationId = intent.getParcelableExtra("donationId", String::class.java)!! // Recebe a referência da doação

        // Configurando o RecyclerView e o Adapter
        messagesRecyclerView = findViewById(R.id.recycler_view_messages)
        messageAdapter = MessageAdapter(sender)
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        messagesRecyclerView.adapter = messageAdapter

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true // Faz o RecyclerView começar de baixo
        messagesRecyclerView.layoutManager = layoutManager
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
            .whereEqualTo("donationId", donationId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@addSnapshotListener
                }

                // Limpa as mensagens antigas antes de adicionar as novas
                val messages = snapshots?.toObjects(Message::class.java) ?: emptyList()
                messageAdapter.submitList(messages) // Atualiza o adapter com a lista de mensagens

                // Alternativa se não usar ListAdapter:
                // messageAdapter.notifyDataSetChanged() // Para atualizações simples, sem ListAdapter
            }
    }

    private fun sendMessage() {
        val messageText = messageInput.text.toString().trim()
        if (messageText.isNotEmpty()) {
            val sortedList = listOf(sender, recipient).sorted()
            val chatId = Hash.generateHash(sortedList.plus(listOf(donationId)))

            val message = Message(
                message = messageText,
                sender = sender,
                recipient = recipient,
                timestamp = Datetime.getCurrentDatetime(),
                // isso garante que o id do chat sempre será o mesmo
                donationId = donationId,
                chatId = chatId
            )

            // Adiciona a mensagem ao Firestore
            firestore.collection("message")
                .add(message)
                .addOnSuccessListener {
                    messageInput.text.clear() // Limpa o campo de entrada de texto
                    loadMessages()
                    messagesRecyclerView.post {
                        // Rolar para a última mensagem após enviar
                        messagesRecyclerView.smoothScrollToPosition(messageAdapter.itemCount - 1)
                    }                }
                .addOnFailureListener { e ->
                    e.printStackTrace() // Lida com falhas no envio
                }

            val chat = Chat(
                id = chatId,
                sender = sender,
                recipient = recipient,
                donation = donationId,
                createdAt = Datetime.getCurrentDatetime(),
                lastMessage = messageText
            )

            firestore.collection("chat").whereEqualTo("id", chat.id).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val querySnapshot = task.result
                        if (querySnapshot != null && !querySnapshot.isEmpty) {
                            val document = querySnapshot.documents[0]
                            document.reference.update("lastMessage", chat.lastMessage)
                                .addOnSuccessListener {
                                    println("Campo lastMessage atualizado com sucesso.")
                                }
                                .addOnFailureListener { e ->
                                    e.printStackTrace()
                                }
                        } else {
                            // Cria o chat apenas se ele não existe
                            firestore.collection("chat")
                                .document(chatId) // Usa o chatId como ID do documento
                                .set(mapOf(
                                    "id" to chat.id,
                                    "sender" to chat.sender,
                                    "recipient" to chat.recipient,
                                    "donation" to chat.donation,
                                    "createdAt" to chat.createdAt,
                                    "lastMessage" to chat.lastMessage
                                ))
                                .addOnSuccessListener {
                                    messageInput.text.clear()
                                    println("Chat criado com sucesso.")
                                }
                                .addOnFailureListener { e ->
                                    e.printStackTrace()
                                }
                        }
                    } else {
                        // Tratar falha ao verificar a existência do documento
                        task.exception?.printStackTrace()
                    }
                }
        }
    }
}
