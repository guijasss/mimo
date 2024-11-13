package com.unipampa.mimo.home.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.unipampa.mimo.R
import com.unipampa.mimo.home.adapters.ChatAdapter
import com.unipampa.mimo.home.entities.Chat

class ChatListActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        firestore = FirebaseFirestore.getInstance()
        chatAdapter = ChatAdapter { chat ->
            // Clique em um chat abre o ChatActivity
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("chatId", chat.id)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recycler_view_chats)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        loadChats()
    }

    private fun loadChats() {
        val currentUserRef = firestore.collection("user").document("currentUserId") // Id do usuário logado

        firestore.collection("chat")
            .whereEqualTo("initiatorId", currentUserRef)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@addSnapshotListener
                }

                val chats = snapshots?.documents?.map { document ->
                    document.toObject(Chat::class.java)!!.copy(id = document.id)
                } ?: emptyList()

                chatAdapter.setChats(chats)
            }
    }
}
