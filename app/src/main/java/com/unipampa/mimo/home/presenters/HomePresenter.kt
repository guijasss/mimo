package com.unipampa.mimo.home.presenters

import com.unipampa.mimo.home.HomeContracts
import com.unipampa.mimo.home.entities.Donation
import com.unipampa.mimo.home.interactors.HomeInteractor

class HomePresenter(private val view: HomeContracts.View) : HomeContracts.Presenter {

    private val interactor: HomeContracts.Interactor = HomeInteractor(this)

    override fun requestDonationsList() {
        this.interactor.downloadDonationsList()
    }

    override fun onDonationsListDownloaded(clientList: ArrayList<Donation>) {
        this.view.onDonationsListRetrieved(clientList)
    }
}
