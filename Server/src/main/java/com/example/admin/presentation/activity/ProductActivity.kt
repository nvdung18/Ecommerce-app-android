package com.example.admin.presentation.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.data.model.Product
import com.example.admin.presentation.adapter.ProductAdapter

class ProductActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val listProduct= mutableListOf<Product>()
        listProduct.add(Product("1","hasf"))
        listProduct.add(Product("2","hasf"))
        listProduct.add(Product("3","hasf"))
        listProduct.add(Product("4","hasf"))
        listProduct.add(Product("5","hasf"))
        listProduct.add(Product("6","hasf"))
        listProduct.add(Product("7","hasf"))
        listProduct.add(Product("8","hasf"))
        listProduct.add(Product("9","hasf"))
        listProduct.add(Product("10","hasf"))

        val adapter=ProductAdapter(listProduct)
        var rvAllProduct=findViewById<RecyclerView>(R.id.rvAllProduct)
        rvAllProduct.adapter=adapter
        rvAllProduct.layoutManager=LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )


    }
}