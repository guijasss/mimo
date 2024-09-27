package com.unipampa.mimo.home

import com.unipampa.mimo.home.entities.Donation


interface HomeContracts {
    interface View {
        fun onDonationsListRetrieved(donationRequests: ArrayList<Donation>)
    }

    interface Interactor {
        fun downloadDonationsList()
    }

    interface Presenter {
        fun requestDonationsList()
        fun onDonationsListDownloaded(clientList: ArrayList<Donation>)
    }
}
