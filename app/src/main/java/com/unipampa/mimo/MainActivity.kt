package com.unipampa.mimo

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unipampa.mimo.databinding.ActivityMainBinding
import com.unipampa.mimo.entities.Campaign
import com.unipampa.mimo.entities.Donation

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configura a RecyclerView para mostrar as campanhas em destaque
        val featuredCampaignsRecyclerView = findViewById<RecyclerView>(R.id.featured_campaigns_recycler)
        featuredCampaignsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Configura a RecyclerView para mostrar as últimas doações
        val latestDonationsRecyclerView = findViewById<RecyclerView>(R.id.latest_donations_recycler)
        latestDonationsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Configura botões de ação
        val donateNowButton = findViewById<Button>(R.id.donate_now_button)
        donateNowButton.setOnClickListener {
            // Ação para ir para a tela de doação
        }

        val viewAllButton = findViewById<TextView>(R.id.view_all_button)
        viewAllButton.setOnClickListener {
            // Ação para ver todas as campanhas
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun getFeaturedCampaigns(): List<Campaign> {
        // Simula dados das campanhas em destaque
        return listOf(
            Campaign("Item 1", "Lorem ipsum dolor sit amet"),
            Campaign("Item 2", "Lorem ipsum dolor sit amet"),
            Campaign("Item 3", "Lorem ipsum dolor sit amet")
        )
    }

    private fun getLatestDonations(): List<Donation> {
        // Simula dados das últimas doações
        return listOf(
            Donation("Doação 1", "Lorem ipsum dolor sit amet"),
            Donation("Doação 2", "Lorem ipsum dolor sit amet"),
            Donation("Doação 3", "Lorem ipsum dolor sit amet")
        )
    }
}