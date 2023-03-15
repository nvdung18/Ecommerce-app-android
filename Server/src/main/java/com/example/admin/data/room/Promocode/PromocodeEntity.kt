package com.example.admin.data.room.Promocode

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PromoCode")
class PromocodeEntity {
    @PrimaryKey() var idPromoCode: String=""
    @ColumnInfo(name = "description") var description: String=""
    @ColumnInfo(name = "discountPercent") var discountPercent:Float=0F
}