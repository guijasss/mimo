package com.unipampa.mimo.home.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.unipampa.mimo.R
import com.unipampa.mimo.home.adapters.ChatAdapter
import com.unipampa.mimo.home.entities.Chat

class ChatListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var currentUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        // Configurar RecyclerView e Adapter
        recyclerView = findViewById(R.id.recycler_view_chats)
        chatAdapter = ChatAdapter { chat -> openChat(chat) }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        firestore = FirebaseFirestore.getInstance()
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        currentUserId = sharedPreferences.getString("currentUserId", "") ?: ""

        loadChats()
    }

    private fun loadChats() {
        firestore.collection("chat")
            .where(
                Filter.or(
                    Filter.equalTo("sender", currentUserId),
                    Filter.equalTo("recipient", currentUserId)
                )
            )
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Toast.makeText(this, "Erro ao carregar chats", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                val chatList = snapshot!!.toObjects(Chat::class.java)
                chatAdapter.setChats(chatList)
            }
    }

    private fun openChat(chat: Chat) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId", chat.id)
        intent.putExtra("recipient", chat.recipient)
        intent.putExtra("sender", chat.sender)
        startActivity(intent)
    }
}
