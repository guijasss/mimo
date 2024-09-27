package com.unipampa.mimo.home.interactors

import com.unipampa.mimo.home.HomeContracts
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.entities.User
import com.unipampa.mimo.home.entities.Location

class HomeInteractor(private val presenter: HomeContracts.Presenter) : HomeContracts.Interactor {
    override fun downloadDonationsList() {
        val donations: ArrayList<Donation> = ArrayList()
        donations.add(
            Donation(
                title = "Camiseta",
                description = "Estado de nova, comprada no ano passado",
                images = arrayListOf("image1.jpg", "image2.jpg", "image3.jpg"),
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

        this.presenter.onDonationsListDownloaded(donations)
    }
}
