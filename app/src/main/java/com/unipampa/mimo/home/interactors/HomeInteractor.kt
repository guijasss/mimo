package com.unipampa.mimo.home.interactors

import com.unipampa.mimo.home.HomeContracts
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.entities.User
import com.unipampa.mimo.home.entities.Location

class HomeInteractor : HomeContracts.Interactor {
    override fun downloadDonationsList(callback: HomeContracts.Interactor.InteractorCallback) {
        // Simulando um download de dados
        val donations: ArrayList<Donation> = ArrayList()
        donations.add(
            Donation(
                id = 1,
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
                id = 2,
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

        callback.onDonationsRetrieved(donations)
    }
}
