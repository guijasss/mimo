package com.unipampa.mimo.home.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
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

        // Verifica se o usuário está autenticado
        if (!isUserAuthenticated()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val currentUserId = sharedPreferences.getString("currentUserId", "default_name")

        val currentUserName = sharedPreferences.getString("name", "default_name")

        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.user_name_text).text = currentUserName

        recyclerView = findViewById(R.id.recycler_view_anuncios)
        donationAdapter = DonationAdapter { donation ->
            val intent = Intent(this, ChatActivity::class.java)

            intent.putExtra("donationId", donation.id)
            intent.putExtra("recipient", donation.creator!!.id)
            intent.putExtra("sender", currentUserId)
            intent.putExtra("currentUserId", currentUserId)
            startActivity(intent)
        }
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
            val filteredDonations = donationsList.filter { it.category == category.name }
            donationAdapter.submitList(filteredDonations)
            Toast.makeText(this, "Clicou em ${category.name}", Toast.LENGTH_SHORT).show()
        }
        recyclerViewCategories.adapter = categoryAdapter

        val fab: FloatingActionButton = findViewById(R.id.button_create_donation_ad)
        fab.setOnClickListener {
            val intent = Intent(this, CreateDonationAdActivity::class.java)
            startActivityForResult(intent, CREATE_DONATION_REQUEST)
        }
    }

    // Verifica se o usuário está autenticado
    private fun isUserAuthenticated(): Boolean {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isAuthenticated", false)
    }

    // Limpa a sessão do usuário quando a aplicação for fechada
    override fun onStop() {
        super.onStop()
        clearUserSession()
    }

    private fun clearUserSession() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isAuthenticated", false).apply()
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
                donationsList.add(0, donation)  // Adiciona a nova doação no início da lista
                donationAdapter.notifyItemInserted(0)  // Notifica o adapter de que um item foi adicionado
                Toast.makeText(this, "Doação criada: ${donation.title}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu) // Inflando o layout do menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_messages -> { // ID do ícone de mensagens
                startActivity(Intent(this, ChatListActivity::class.java))
                true
            }
            R.id.action_logout -> {
                // Lógica do botão de logout
                handleLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleLogout() {
        // Apagar o valor de "isAuthenticated" para false nas SharedPreferences
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isAuthenticated", false) // Definir como false
        editor.apply()

        // Redirecionar para a tela de login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Opcional, para finalizar a MainActivity e não permitir voltar
    }

    companion object {
        private const val CREATE_DONATION_REQUEST = 1
        const val DONATION_EXTRA = "DONATION_EXTRA"
    }
}
