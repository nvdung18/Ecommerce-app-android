package com.example.admin.data.model.Account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.admin.data.model.User.UserEntity

@Entity(tableName = "account", foreignKeys = [ForeignKey(
    entity = UserEntity::class,
    parentColumns = ["idUser"],
    childColumns = ["idF_User"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
)])
class AccountEntity {
    @PrimaryKey() var idAccount:String=""
    @ColumnInfo(name = "userName") var userName:String=""
    @ColumnInfo(name = "password") var password:String=""
    @ColumnInfo(name = "method") var method:String=""
    @ColumnInfo(name = "idF_User") var idUser:String=""
}