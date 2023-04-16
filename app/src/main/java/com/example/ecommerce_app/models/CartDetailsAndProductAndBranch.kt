package com.example.ecommerce_app.models

import android.os.Parcel
import android.os.Parcelable

data class CartDetailsAndProductAndBranch(
    var quantity:Int=0,
    var idCart:String="",
    var idProduct:String="",
    var nameProduct: String = "",
    var image: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var type: String = "",
    var sale:Float=0F,
    var soldQuantity: Int = 0,
    var idBranch: String = "",
    var nameBranch: String = ""
): Parcelable {


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(quantity)
        parcel.writeString(idCart)
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

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartDetailsAndProductAndBranch> {
        override fun createFromParcel(parcel: Parcel): CartDetailsAndProductAndBranch {
            return CartDetailsAndProductAndBranch(
                parcel.readInt() ?: 1,
                        parcel.readString() ?: "",
                        parcel.readString() ?: "",
                        parcel.readString() ?: "",
                        parcel.readString() ?: "",
                        parcel.readDouble() ?: 0.0,
                        parcel.readString() ?: "",
                        parcel.readString() ?: "",
                        parcel.readFloat() ?: 0F,
                        parcel.readInt() ?: 0,
                        parcel.readString() ?: "",
                        parcel.readString() ?: "",

            )
        }

        override fun newArray(size: Int): Array<CartDetailsAndProductAndBranch?> {
            return arrayOfNulls(size)
        }
    }

}