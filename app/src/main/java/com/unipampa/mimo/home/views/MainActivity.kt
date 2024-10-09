package com.unipampa.mimo.home.views

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unipampa.mimo.R
import com.unipampa.mimo.home.HomeContracts
import com.unipampa.mimo.home.adapters.CategoryAdapter
import com.unipampa.mimo.home.adapters.DonationAdapter
import com.unipampa.mimo.home.entities.Category
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.interactors.HomeInteractor
import com.unipampa.mimo.home.presenters.HomePresenter


class MainActivity : AppCompatActivity(), HomeContracts.View {
    private lateinit var presenter: HomeContracts.Presenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var donationAdapter: DonationAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categories: List<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view_anuncios)
        donationAdapter = DonationAdapter()
        recyclerView.adapter = donationAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val interactor = HomeInteractor()
        presenter = HomePresenter(interactor, this)
        presenter.requestDonationsList()

        categories = listOf(
            Category("Alimentos", R.drawable.ic_cutlery),
            Category("Roupas", R.drawable.ic_tshirt),
            Category("Eletrônicos", R.drawable.ic_wall),
            Category("Brinquedos", R.drawable.ic_toy)
        )

        val recyclerViewCategories = findViewById<RecyclerView>(R.id.recycler_view_categories)
        recyclerViewCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(categories) { category ->
            Toast.makeText(this, "Clicou em ${category.name}", Toast.LENGTH_SHORT).show()
        }
        recyclerViewCategories.adapter = categoryAdapter
    }

    override fun onDonationsListRetrieved(donationRequests: ArrayList<Donation>) {
        this.donationAdapter.submitList(donationRequests)
    }
}
