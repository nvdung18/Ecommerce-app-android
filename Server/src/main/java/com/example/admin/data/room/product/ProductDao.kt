package com.example.admin.data.room.product

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product order by idProduct desc ")
    fun getAllProduct(): LiveData<List<ProductEntity>>
    @Insert()
    fun insertProduct(product:ProductEntity)
}