package com.example.admin.data.room.Product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    fun insertAll(list: List<ProductEntity>)

//    @Insert
//    fun insertProduct(productEntity: ProductEntity): Int

    @Query("Select * from Product")
    fun queryAllProduct(): List<ProductEntity>
}