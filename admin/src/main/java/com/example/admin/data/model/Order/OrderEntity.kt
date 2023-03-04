package com.example.admin.data.model.Order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Order")
class OrderEntity {
    @PrimaryKey() var idOrder:String=""
    @ColumnInfo(name = "status") var status:String=""
//    @ColumnInfo(name = "deliveryTime") var deliveryTime:String=""
    @ColumnInfo(name = "orderNotes") var orderNotes:String=""
    @ColumnInfo(name = "deliveryCharges") var deliveryCharges:Double=0.0

    @ColumnInfo(name = "idF_Account") var idAccount:String=""
    @ColumnInfo(name = "idF_Payment") var idPayment:String=""
    @ColumnInfo(name = "idF_Promocode") var idPromodeCode:String=""
    @ColumnInfo(name = "idF_Checkout") var idF_Checkout:String=""
}