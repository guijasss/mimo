package com.unipampa.mimo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unipampa.mimo.R
import com.unipampa.mimo.entities.Campaign

class CampaignAdapter(private val campaigns: List<Campaign>) :
    RecyclerView.Adapter<CampaignAdapter.CampaignViewHolder>() {

    class CampaignViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.campaign_title)
        val descriptionTextView: TextView = view.findViewById(R.id.campaign_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_campaign, parent, false)
        return CampaignViewHolder(view)
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        val campaign = campaigns[position]
        holder.titleTextView.text = campaign.title
        holder.descriptionTextView.text = campaign.description
    }

    override fun getItemCount() = campaigns.size
}
