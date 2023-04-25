package com.example.admin.data.model

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

    var description:String="",
    var discountPercent:Float=0F
)