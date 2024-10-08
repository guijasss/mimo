package com.unipampa.mimo.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unipampa.mimo.R
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.views.MainActivity


class DonationAdapter(private val donations: List<Donation>) : RecyclerView.Adapter<DonationAdapter.DonationViewHolder>() {

    class DonationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.anuncio_titulo)
        private val description: TextView = itemView.findViewById(R.id.anuncio_descricao)
        private val category: TextView = itemView.findViewById(R.id.anuncio_categoria)

        fun bind(donation: Donation) {
            title.text = donation.title
            description.text = donation.description
            category.text = donation.category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_donation, parent, false)
        return DonationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        holder.bind(donations[position])
    }

    override fun getItemCount(): Int = donations.size
}
