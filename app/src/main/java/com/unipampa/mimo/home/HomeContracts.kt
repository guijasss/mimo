package com.unipampa.mimo.home

import com.unipampa.mimo.home.entities.Donation


interface HomeContracts {
    interface View {
        fun onDonationsListRetrieved(donationRequests: ArrayList<Donation>)
    }

    interface Presenter {
        fun requestDonationsList()
    }

    interface Interactor {
        fun downloadDonationsList(callback: InteractorCallback)

        interface InteractorCallback {
            fun onDonationsRetrieved(donations: ArrayList<Donation>)
        }
    }
}
