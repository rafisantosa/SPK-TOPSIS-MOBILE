package com.example.spktopsis

import android.os.Parcel
import android.os.Parcelable


data class Barang(
    val id: Int,
    val nama: String,
    val harga: Double,
    val jumlahStok: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nama)
        parcel.writeDouble(harga)
        parcel.writeInt(jumlahStok)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Barang> {
        override fun createFromParcel(parcel: Parcel): Barang {
            return Barang(parcel)
        }

        override fun newArray(size: Int): Array<Barang?> {
            return arrayOfNulls(size)
        }
    }
}

