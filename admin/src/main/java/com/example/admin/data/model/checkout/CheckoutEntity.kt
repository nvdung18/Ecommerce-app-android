package com.example.admin.data.model.checkout

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.admin.data.model.Account.AccountEntity
import com.example.admin.data.model.User.UserEntity

@Entity(tableName = "Checkout", foreignKeys = [ForeignKey(
    entity = AccountEntity::class,
    parentColumns = ["idAccount"],
    childColumns = ["idF_Account"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
)])
class CheckoutEntity {
    @PrimaryKey() var idCheckout: String=""
    @ColumnInfo(name = "recipientName") var recipientName:String=""
    @ColumnInfo(name = "recipientPhoneNumber") var recipientPhoneNumber:Int=0
    @ColumnInfo(name = "recipientEmail") var recipientEmail:String=""
    @ColumnInfo(name = "recipientAddress") var recipientAddress:String=""
    @ColumnInfo(name = "idF_Account") var idAccount:String=""
}