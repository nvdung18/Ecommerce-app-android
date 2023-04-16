package com.example.admin.data.room.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.admin.data.model.OrderDetailsAndProduct
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.branch.BranchDao
import com.example.admin.data.room.branch.BranchEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application)  {
    private var application=application
    private lateinit var productDao:ProductDao
    private lateinit var branchDao:BranchDao
    private lateinit var instance:AppDatabase
    lateinit var allProduct: LiveData<List<ProductEntity>>
    lateinit var allBranch:LiveData<List<BranchEntity>>
    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            instance=AppDatabase.getInstance(application)
            productDao=instance.productDao()
            branchDao=instance.branchDao()

            allProduct=productDao.getAllProduct()
            allBranch=branchDao.getAllBranchOrderASC()
        }
    }

    fun insert(product:ProductEntity)=viewModelScope.launch ( Dispatchers.IO ){
        productDao.insertProduct(product)
    }

    fun deleteProduct(product:ProductEntity)=viewModelScope.launch (Dispatchers.IO){
        productDao.deleteProduct(product)
    }

    fun updateProduct(product:ProductEntity)=viewModelScope.launch (Dispatchers.IO){
        productDao.updateProduct(product)
    }

    fun getProductAndOrderDtById(idProduct:String):List<OrderDetailsAndProduct>{
        return productDao.getProductJoinOrDetailsById(idProduct)
    }


}