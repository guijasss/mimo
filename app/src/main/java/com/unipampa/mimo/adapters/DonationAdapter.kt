package com.unipampa.mimo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unipampa.mimo.R
import com.unipampa.mimo.entities.Donation

class DonationAdapter(private val donations: List<Donation>) :
    RecyclerView.Adapter<DonationAdapter.DonationViewHolder>() {

    class DonationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.donation_title)
        val descriptionTextView: TextView = view.findViewById(R.id.donation_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_donation, parent, false)
        return DonationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        val donation = donations[position]
        holder.titleTextView.text = donation.title
        holder.descriptionTextView.text = donation.description
    }

    override fun getItemCount() = donations.size
}
