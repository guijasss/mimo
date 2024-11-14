package com.unipampa.mimo.home.entities

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class Chat(
    val id: String = "",
    val sender: String = "",
    val recipient: String = "",
    val donation: String = "",
    val lastMessage: String = "",
    val createdAt: String = "",
    val senderData: User? = null,
    val recipientData: User? = null
) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(sender)
        parcel.writeString(recipient)
        parcel.writeString(donation) // Escreve a data no Parcel
        parcel.writeString(lastMessage)
        parcel.writeString(createdAt)
        parcel.readParcelable(User::class.java.classLoader, User::class.java) ?: User("", "", "", "", "", "")
        parcel.readParcelable(User::class.java.classLoader, User::class.java) ?: User("", "", "", "", "", "")
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

