package com.example.ecommerce_app.models

import android.os.Parcel
import android.os.Parcelable

data class BranchEntity (
    var idBranch: String = "",
    var nameBranch: String = ""
): Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(idBranch)
        dest.writeString(nameBranch)
    }

    companion object CREATOR : Parcelable.Creator<BranchEntity> {
        override fun createFromParcel(parcel: Parcel): BranchEntity {
            return BranchEntity(
                parcel.readString() ?: "",
                parcel.readString() ?: ""
            )
        }

        override fun newArray(size: Int): Array<BranchEntity?> {
            return arrayOfNulls(size)
        }
    }
}