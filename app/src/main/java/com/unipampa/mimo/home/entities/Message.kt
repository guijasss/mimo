package com.unipampa.mimo.home.entities

import android.os.Parcel
import android.os.Parcelable


data class Message(
    val message: String = "",
    val sender: String = "",
    val recipient: String = "",
    val timestamp: String = "",
    val chatId: String = "",
    val donationId: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeString(sender)
        parcel.writeString(recipient)
        parcel.writeString(timestamp) // Escreve a data no Parcel
        parcel.writeString(chatId)
        parcel.writeString(donationId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Message> {
        override fun createFromParcel(parcel: Parcel): Message {
            return Message(parcel)
        }

        override fun newArray(size: Int): Array<Message?> {
            return arrayOfNulls(size)
        }
    }
}
