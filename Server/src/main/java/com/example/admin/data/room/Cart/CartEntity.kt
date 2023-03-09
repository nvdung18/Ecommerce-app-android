package com.example.admin.data.room.Cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.admin.data.room.Account.AccountEntity

@Entity(tableName = "Cart",foreignKeys = [ForeignKey(
    entity = AccountEntity::class,
    parentColumns = ["idAccount"],
    childColumns = ["idAccount"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
)])
class CartEntity {
    @PrimaryKey() var idCart: String=""
    @ColumnInfo(name = "idAccount") var idAccount:String=""
}