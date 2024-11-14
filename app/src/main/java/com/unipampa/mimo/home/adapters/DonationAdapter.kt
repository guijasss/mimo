package com.unipampa.mimo.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unipampa.mimo.R
import com.unipampa.mimo.home.entities.Donation

class DonationAdapter(
    private val contactClickListener: (Donation) -> Unit // Listener para o botão "Contatar"
) : ListAdapter<Donation, DonationViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_donation, parent, false)
        return DonationViewHolder(view, contactClickListener) // Passe o listener para o ViewHolder
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

// ViewHolder
class DonationViewHolder(
    itemView: View,
    private val contactClickListener: (Donation) -> Unit // Listener do botão "Contatar"
) : RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.anunciante_nome)
    private val locationTextView: TextView = itemView.findViewById(R.id.anunciante_local)
    private val categoryTextView: TextView = itemView.findViewById(R.id.anuncio_categoria)
    private val titleTextView: TextView = itemView.findViewById(R.id.anuncio_titulo)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.anuncio_descricao)
    private val contactButton: Button = itemView.findViewById(R.id.contatar_button)

    fun bind(ad: Donation) {
        nameTextView.text = ad.creator!!.name
        locationTextView.text = ad.creator!!.city // concat com estado
        categoryTextView.text = ad.category
        titleTextView.text = ad.title
        descriptionTextView.text = ad.description

        // Configura o clique no botão "Contatar"
        contactButton.setOnClickListener {
            contactClickListener(ad) // Chama o listener com a doação atual
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Donation>() {
    override fun areItemsTheSame(oldItem: Donation, newItem: Donation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Donation, newItem: Donation): Boolean {
        return oldItem == newItem
    }
}
