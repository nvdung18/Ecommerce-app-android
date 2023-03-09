package com.example.admin.data.room.Payment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Payment")
class PaymentEntity {
    @PrimaryKey() var idPayment: String=""
    @ColumnInfo(name = "namePayment") var namePayment:String=""
    @ColumnInfo(name = "totalPayment") var totalPayment: Double=0.0
}