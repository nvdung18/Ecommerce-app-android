package com.example.ecommerce_app.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

data class BrandAndModel(
    var idProduct: String = "",
    var nameProduct: String = "",
    var image: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var type: String = "",
    var sale: Float = 0F,
    var soldQuantity: Int = 0,
    var idBranch: String = "",
    var nameBranch: String = ""
) : Parcelable {

    // Implement the writeToParcel() method to write the object's state to a parcel.
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idProduct)
        parcel.writeString(nameProduct)
        parcel.writeString(image)
        parcel.writeDouble(price)
        parcel.writeString(description)
        parcel.writeString(type)
        parcel.writeFloat(sale)
        parcel.writeInt(soldQuantity)
        parcel.writeString(idBranch)
        parcel.writeString(nameBranch)
    }

    // Implement the describeContents() method to describe the contents of the object's Parcelable representation.
    override fun describeContents(): Int {
        return 0
    }

    // Implement the CREATOR property to create the object from a Parcel.
    companion object CREATOR : Parcelable.Creator<BrandAndModel> {
        override fun createFromParcel(parcel: Parcel): BrandAndModel {
            return BrandAndModel(
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readDouble(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readFloat(),
                parcel.readInt(),
                parcel.readString() ?: "",
                parcel.readString() ?: ""
            )
        }

        override fun newArray(size: Int): Array<BrandAndModel?> {
            return arrayOfNulls(size)
        }
    }
}