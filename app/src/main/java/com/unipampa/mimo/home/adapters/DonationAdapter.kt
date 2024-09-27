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


class DonationAdapter(
    private val activity: MainActivity,
    private val context: Context,
    private val donations: ArrayList<Donation>

) : RecyclerView.Adapter<DonationAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userProfileImage: ImageView = view.findViewById(R.id.user_profile_image)
        val userName: TextView = view.findViewById(R.id.user_name)
        val userLocation: TextView = view.findViewById(R.id.user_location)
        val itemTitle: TextView = view.findViewById(R.id.item_title)
        val itemDescription: TextView = view.findViewById(R.id.item_description)
        val itemImagesRecyclerView: RecyclerView = view.findViewById(R.id.item_images_recycler_view)
    }

    fun addDonation(donation: Donation) {
        this.donations.add(donation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_donation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = donations[position]

        // Usar Glide para carregar a imagem do perfil do usuário
        Glide.with(holder.itemView.context)
            .load(item.requester.profilePicture) // Supondo que isso seja uma URL
            .into(holder.userProfileImage)

        holder.userName.text = item.requester.name
        holder.userLocation.text = item.requester.location.city
        holder.itemTitle.text = item.title
        holder.itemDescription.text = item.description

        // Configurar o adapter para o RecyclerView das imagens
        holder.itemImagesRecyclerView.layoutManager = LinearLayoutManager(holder.itemImagesRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        val imageAdapter = ImageAdapter(item.images, holder.itemImagesRecyclerView.context)
        holder.itemImagesRecyclerView.adapter = imageAdapter
    }

    override fun getItemCount() = donations.size
}
