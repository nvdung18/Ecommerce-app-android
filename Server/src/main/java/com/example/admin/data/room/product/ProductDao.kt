package com.example.admin.data.room.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    fun getAllProduct():List<ProductEntity>
    @Insert()
    fun insertProduct(product:ProductEntity)
}