package com.example.admin.data.room.User

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity {
    @PrimaryKey() var idUser:String=""
    @ColumnInfo(name = "fullName") var fullName:String=""
    @ColumnInfo(name = "gender") var gender:String=""
    @ColumnInfo(name = "address") var address:String=""
    @ColumnInfo(name = "phoneNumber") var phoneNumber:Int=0
    @ColumnInfo(name = "email") var email:String=""
    @ColumnInfo(name = "role") var role:Int=0
}