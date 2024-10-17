package com.unipampa.mimo.home.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    private val donationsList = ArrayList<Donation>() // Lista para armazenar as doações

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
            Category("Construção", R.drawable.ic_wall),
            Category("Brinquedos", R.drawable.ic_toy)
        )

        val recyclerViewCategories = findViewById<RecyclerView>(R.id.recycler_view_categories)
        recyclerViewCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(categories) { category ->
            Toast.makeText(this, "Clicou em ${category.name}", Toast.LENGTH_SHORT).show()
        }
        recyclerViewCategories.adapter = categoryAdapter

        val fab: FloatingActionButton = findViewById(R.id.button_create_donation_ad)
        fab.setOnClickListener {
            val intent = Intent(this, CreateDonationAdActivity::class.java)
            startActivityForResult(intent, CREATE_DONATION_REQUEST)
        }
    }

    override fun onDonationsListRetrieved(donationRequests: ArrayList<Donation>) {
        this.donationsList.clear() // Limpa a lista anterior
        this.donationsList.addAll(donationRequests) // Adiciona as doações recebidas
        this.donationAdapter.submitList(donationsList) // Atualiza o adapter
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_DONATION_REQUEST && resultCode == RESULT_OK) {
            val donation: Donation? = data?.getParcelableExtra(DONATION_EXTRA)
            if (donation != null) {
                donationsList.add(donation) // Adiciona a nova doação à lista
                donationAdapter.notifyItemInserted(donationsList.size - 1) // Notifica o adapter
                Toast.makeText(this, "Doação criada: ${donation.title}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val CREATE_DONATION_REQUEST = 1
        const val DONATION_EXTRA = "DONATION_EXTRA"
    }
}
