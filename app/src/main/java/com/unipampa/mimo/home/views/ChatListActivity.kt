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
import com.unipampa.mimo.home.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ChatsApi {
    @GET("chats") // O endpoint agora é só "users", o parâmetro será passado via query
    fun getChats(@Query("username") username: String): Call<List<Chat>>
}


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

        loadChats(currentUserId)
    }

    override fun onResume() {
        super.onResume()
        loadChats(currentUserId)
    }

    private fun loadChats(username: String) {
        val retrofit = RetrofitClient.build()
        val chatApi = retrofit.create(ChatsApi::class.java)

        chatApi.getChats(username).enqueue(object : Callback<List<Chat>> {
            override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
                if (response.isSuccessful) {
                    val chatsResponse = response.body()

                    if (chatsResponse != null) {
                        chatAdapter.setChats(chatsResponse)
                    }
                    else {
                        Toast.makeText(this@ChatListActivity, "Erro ao carregar chat!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
                chatAdapter.setChats(ArrayList())
                Toast.makeText(this@ChatListActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openChat(chat: Chat) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("donationId", chat.donation)
        intent.putExtra("recipient", chat.recipient)
        intent.putExtra("sender", chat.sender)
        startActivity(intent)
    }
}
