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


class MainActivity : AppCompatActivity() {
    // private val presenter: HomeContracts.Presenter = HomePresenter(this)
    private val adapter = DonationAdapter(this, applicationContext, ArrayList<Donation>())
    private val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}