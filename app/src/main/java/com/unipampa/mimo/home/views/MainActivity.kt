package com.unipampa.mimo.home.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unipampa.mimo.R
import com.unipampa.mimo.home.HomeContracts
import com.unipampa.mimo.home.adapters.DonationAdapter
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.interactors.HomeInteractor
import com.unipampa.mimo.home.presenters.HomePresenter

class MainActivity : AppCompatActivity(), HomeContracts.View {
    private lateinit var presenter: HomeContracts.Presenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var donationAdapter: DonationAdapter
    private lateinit var categoriaAlimentos: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Verifica se o usuário está autenticado
        if (!isUserAuthenticated()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view_anuncios)
        donationAdapter = DonationAdapter()
        recyclerView.adapter = donationAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val interactor = HomeInteractor()
        presenter = HomePresenter(interactor, this)
        presenter.requestDonationsList()

        categoriaAlimentos = findViewById(R.id.categoria_alimentos)

        categoriaAlimentos.setOnClickListener {
            Toast.makeText(this, "Você clicou em Alimentos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isUserAuthenticated(): Boolean {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isAuthenticated", false)
    }

    override fun onDonationsListRetrieved(donationRequests: ArrayList<Donation>) {
        this.donationAdapter.submitList(donationRequests)
    }
}
