package com.example.admin.data.room.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.admin.data.model.BrandAndModel
import com.example.admin.data.room.product.ProductEntity

@Dao
interface ProductDao {

    @Insert
    fun insertAll(list: List<ProductEntity>)

//    @Insert
//    fun insertProduct(productEntity: ProductEntity): Int

    @Query("Select * from Product")
    fun queryAllProduct(): List<ProductEntity>

    @Insert
    fun insertProduct(productEntity: ProductEntity)

    @Query("Select * from Product inner join Branch on Product.idBranch = Branch.idBranch")
    fun getAllProductByBranch():List<BrandAndModel>

}
