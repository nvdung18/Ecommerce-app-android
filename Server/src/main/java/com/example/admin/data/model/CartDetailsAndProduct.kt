package com.example.admin.data.model

data class CartDetailsAndProduct (
    var quantity:Int=0,
    var idCart:String="",
    var idProduct:String="",
    var nameProduct: String = "",
    var image: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var type: String = "",
    var sale:Float=0F,
    var soldQuantity: Int = 0,
    var idBranch: String = ""
)