package com.example.admin.data.room.product

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.admin.data.model.BrandAndModel
import com.example.admin.data.model.Product
import com.example.admin.data.room.product.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product order by idProduct desc ")
    fun getAllProduct(): LiveData<List<ProductEntity>>

    @Delete()
    fun deleteProduct(productEntity: ProductEntity)
    
    @Insert
    fun insertAll(list: List<ProductEntity>)

    @Query("Select * from Product where idProduct=:idProduct")
    fun getProductByIDServer(idProduct: String):ProductEntity

//    @Insert
//    fun insertProduct(productEntity: ProductEntity): Int

    @Query("Select * from Product")
    fun queryAllProduct(): List<ProductEntity>

    @Insert
    fun insertProduct(productEntity: ProductEntity)

    @Query("Select * from Product inner join Branch on Product.idBranch = Branch.idBranch")
    fun getAllProductByBranch():List<BrandAndModel>
}

