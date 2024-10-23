package com.unipampa.mimo.home.entities

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class Donation(
    val category: String,
    val description: String,
    val id: String,
    val title: String
//    val requester: User
) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
//        parcel.readParcelable(User::class.java.classLoader, User::class.java) ?: User("", "", "", "", "", arrayListOf())    )
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(category)
//        parcel.writeParcelable(requester, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Donation> {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun createFromParcel(parcel: Parcel): Donation {
            return Donation(parcel)
        }

        override fun newArray(size: Int): Array<Donation?> {
            return arrayOfNulls(size)
        }
    }
}
