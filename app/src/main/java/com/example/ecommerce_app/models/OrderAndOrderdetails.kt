package com.example.ecommerce_app.models

class OrderAndOrderdetails(
    var idOrder:String ="",
    var status:String ="",
    var orderNotes:String="",
    var deliveryCharges:Float=0F,
    var productMoney:Double=0.0,
    var idPayment:String="",
    var idPromoCode:String="",
    var idCheckout:String="",

    var idProduct:String="",
    var quantity:Int=0,
){
    override fun toString(): String {
        return ("${idOrder.toString()}" +
                "${status.toString()}" +
                "${orderNotes.toString()}" +
                "${deliveryCharges.toString()}" +
                "${productMoney.toString()}" +
                "${idPayment.toString()}" +
                "${idPromoCode.toString()}" +
                "${idCheckout.toString()}" +
                "${idProduct.toString()}" +
                "${quantity.toString()}")
    }
}