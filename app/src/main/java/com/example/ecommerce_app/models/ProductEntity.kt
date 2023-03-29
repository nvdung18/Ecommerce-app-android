package com.example.ecommerce_app.models

import androidx.room.ColumnInfo

data class ProductEntity(
    var idProduct: String = "",
    var nameProduct: String = "",
    var image: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var type: String = "",
    var sale:Float=0F,
    var soldQuantity: Int = 0,
    var idBranch: String = ""
)