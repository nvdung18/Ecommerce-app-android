package com.example.admin.data.model

data class BrandAndModel (
    var idProduct: String = "",
    var nameProduct: String = "",
    var image: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var type: String = "",
    var sale:Float=0F,
    var soldQuantity: Int = 0,
    var idBranch: String = "",
    var nameBranch: String = ""
)