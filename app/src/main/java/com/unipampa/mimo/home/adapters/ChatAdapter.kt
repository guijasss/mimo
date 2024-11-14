package com.unipampa.mimo.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unipampa.mimo.R
import com.unipampa.mimo.home.entities.Chat


class ChatAdapter(private val onChatClick: (Chat) -> Unit) :
    ListAdapter<Chat, ChatAdapter.ChatViewHolder>(ChatDiffCallback()) {

    private var currentUser: String = ""

    // Função para passar o currentUser da Activity para o Adapter
    fun setCurrentUser(username: String) {
        currentUser = username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = getItem(position)
        holder.setCurrentUser(currentUser) // Passa currentUser para o ViewHolder
        holder.bind(chat)
        holder.itemView.setOnClickListener { onChatClick(chat) }
    }

    fun setChats(chats: List<Chat>) {
        submitList(chats)
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.chat_user_name)
        private val lastMessageTextView: TextView = itemView.findViewById(R.id.chat_last_message)
        private var currentUser = ""

        fun setCurrentUser(username: String) {
            currentUser = username
        }

        fun bind(chat: Chat) {
            // Usando safe call (?.) para evitar NullPointerException
            userNameTextView.text = if (currentUser == chat.sender) {
                chat.recipientData?.name ?: "Nome desconhecido"
            } else {
                chat.senderData?.name ?: "Nome desconhecido"
            }
            lastMessageTextView.text = chat.lastMessage
        }
    }
}


// Implementa DiffUtil para comparar mudanças na lista de chats
class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }
}
