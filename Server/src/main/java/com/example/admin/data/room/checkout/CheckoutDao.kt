package com.example.admin.data.room.checkout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CheckoutDao {

    @Query("SELECT * FROM Checkout Where idCheckout=:idCheckout")
    fun getCheckoutById(idCheckout:String):CheckoutEntity

    @Insert()
    fun insertCheckout(checkout:CheckoutEntity): Long

    @Query("Select * from Checkout")
    fun getCheckout(): List<CheckoutEntity>
}