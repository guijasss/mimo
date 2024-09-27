package com.unipampa.mimo.home.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unipampa.mimo.R
import com.unipampa.mimo.home.HomeContracts
import com.unipampa.mimo.home.adapters.DonationAdapter
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.presenters.HomePresenter


class MainActivity : AppCompatActivity(), HomeContracts.View {
    private val presenter: HomeContracts.Presenter = HomePresenter(this)
    private val adapter = DonationAdapter(this, applicationContext, ArrayList<Donation>())
    private val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.addRecyclerView()

        this.presenter.requestDonationsList()
    }

    override fun onDonationsListRetrieved(donationRequests: ArrayList<Donation>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        for (donation in donationRequests) {
            this.adapter.addDonation(donation)
        }

        recyclerView.layoutManager = this.layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = this.adapter
    }

    private fun addRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager = this.layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = this.adapter
    }
}