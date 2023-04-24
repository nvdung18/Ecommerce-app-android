package com.example.ecommerce_app.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class UserEntity (
     var idUser:String="",
     var fullName:String="",
     var gender:String="",
     var address:String="",
     var phoneNumber:Int=0,
     var email: String = "",
     var role:Int=0
)