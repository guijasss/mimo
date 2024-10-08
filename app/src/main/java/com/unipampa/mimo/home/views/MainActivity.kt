package com.unipampa.mimo.home.views

import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unipampa.mimo.R
import com.unipampa.mimo.home.adapters.DonationAdapter
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.entities.Location
import com.unipampa.mimo.home.entities.User


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var donationAdapter: DonationAdapter
    private lateinit var searchBar: EditText
    private lateinit var categoriasTitle: TextView
    private lateinit var categoriaAlimentos: FrameLayout
    private lateinit var categoriaRoupas: FrameLayout
    private lateinit var categoriaEletronicos: FrameLayout
    private lateinit var categoriaBrinquedos: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view_anuncios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Dados hardcoded
        val donations: ArrayList<Donation> = ArrayList()
        donations.add(
            Donation(
                title = "Camiseta",
                description = "Estado de nova, comprada no ano passado",
                category = "Roupas",
                requester = User(
                    name = "Maria da Silva",
                    location = Location(
                        city = "Alegrete",
                        state = "RS"
                    ),
                    profilePicture = "profile.jpg",
                    username = "mariasilva",
                    phoneNumbers = arrayListOf("55999341223")
                )
            )
        )

        donations.add(
            Donation(
                title = "Camiseta",
                description = "Estado de nova, comprada no ano passado",
                category = "Roupas",
                requester = User(
                    name = "Maria da Silva",
                    location = Location(
                        city = "Alegrete",
                        state = "RS"
                    ),
                    profilePicture = "profile.jpg",
                    username = "mariasilva",
                    phoneNumbers = arrayListOf("55999341223")
                )
            )
        )

        donationAdapter = DonationAdapter(donations)
        recyclerView.adapter = donationAdapter

        searchBar = findViewById(R.id.search_bar)
        categoriasTitle = findViewById(R.id.categorias_title)
        categoriaAlimentos = findViewById(R.id.categoria_alimentos)
        categoriaRoupas = findViewById(R.id.categoria_roupas)
        categoriaEletronicos = findViewById(R.id.categoria_eletronicos)
        categoriaBrinquedos = findViewById(R.id.categoria_brinquedos)

        categoriaAlimentos.setOnClickListener {
            // Ação ao clicar em Alimentos
            Toast.makeText(this, "Você clicou em Alimentos", Toast.LENGTH_SHORT).show()
        }

        categoriaRoupas.setOnClickListener {
            // Ação ao clicar em Roupas
            Toast.makeText(this, "Você clicou em Roupas", Toast.LENGTH_SHORT).show()
        }

        categoriaEletronicos.setOnClickListener {
            // Ação ao clicar em Eletrônicos
            Toast.makeText(this, "Você clicou em Eletrônicos", Toast.LENGTH_SHORT).show()
        }

        categoriaBrinquedos.setOnClickListener {
            // Ação ao clicar em Brinquedos
            Toast.makeText(this, "Você clicou em Brinquedos", Toast.LENGTH_SHORT).show()
        }
    }
}
