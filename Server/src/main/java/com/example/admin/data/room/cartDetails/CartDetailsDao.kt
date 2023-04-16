package com.example.admin.data.room.cartDetails

import androidx.room.*
import com.example.admin.data.model.CartDetailsAndProduct
import com.example.admin.data.model.CartDetailsAndProductAndBranch

@Dao
interface CartDetailsDao {

    @Insert
    fun insertCardDetail(cart: CartDetailsEntity): Long

    @Query("Select * from CartDetails inner join Product on CartDetails.idProduct = Product.idProduct inner join Branch on Product.idBranch = Branch.idBranch  Where idCart = :idCart")
    fun queryAllCartDetails_Product(idCart: String): List<CartDetailsAndProductAndBranch>

    @Update
    fun updateProductInCartDetails(cart: CartDetailsEntity): Int

    @Delete
    fun deleteCartDetails(cart: CartDetailsEntity): Int

    @Query("Select * from CartDetails where idCart = :idCart and idProduct = :idProduct")
    fun queryAllCartDetailsByIdCart_IdProduct(idCart: String, idProduct: String): CartDetailsEntity

    @Query("Update CartDetails set quantity = :quantity Where idCart = :idCart and idProduct = :idProduct")
    fun updateQuantityCartDetailsByIdCart_IdProduct(quantity: String, idCart: String, idProduct: String): Int
}
