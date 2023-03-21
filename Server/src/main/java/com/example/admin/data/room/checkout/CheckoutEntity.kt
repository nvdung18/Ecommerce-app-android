package com.example.admin.data.room.checkout

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.admin.data.room.Account.AccountEntity

@Entity(tableName = "Checkout", foreignKeys = [ForeignKey(
    entity = AccountEntity::class,
    parentColumns = ["idAccount"],
    childColumns = ["idAccount"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
)])
data class CheckoutEntity (
    @PrimaryKey() var idCheckout: String="",
    @ColumnInfo(name = "recipientName") var recipientName:String="",
    @ColumnInfo(name = "recipientPhoneNumber") var recipientPhoneNumber:Int=0,
    @ColumnInfo(name = "recipientEmail") var recipientEmail:String="",
    @ColumnInfo(name = "recipientAddress") var recipientAddress:String="",
    @ColumnInfo(name = "idAccount") var idAccount:String=""
)