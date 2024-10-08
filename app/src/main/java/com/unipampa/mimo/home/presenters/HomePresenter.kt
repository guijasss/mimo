package com.unipampa.mimo.home.presenters

import com.unipampa.mimo.home.HomeContracts
import com.unipampa.mimo.home.entities.Donation

class HomePresenter(private val interactor: HomeContracts.Interactor, private val view: HomeContracts.View) : HomeContracts.Presenter {
    override fun requestDonationsList() {
        this.interactor.downloadDonationsList(object : HomeContracts.Interactor.InteractorCallback {
            override fun onDonationsRetrieved(donations: ArrayList<Donation>) {
                view.onDonationsListRetrieved(donations)
            }
        })
    }
}
