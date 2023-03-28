package com.example.admin.data.room.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {

    @Insert
    fun insertCart(cart: CartEntity)

    @Query("Select * FROM Cart")
    fun queryAllCart(): List<CartEntity>

    @Query("Select * FROM Cart where idAccount = :idAccount")
    fun queryIdCartByIdAccount(idAccount: String): CartEntity
}
