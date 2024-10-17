package com.unipampa.mimo.home.entities

import android.os.Parcel
import android.os.Parcelable


data class User(
    val name: String,
    val city: String,
    val state: String,
    val profilePicture: String,
    val username: String,
    val phoneNumbers: ArrayList<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: ArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(profilePicture)
        parcel.writeString(username)
        parcel.writeStringList(phoneNumbers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
