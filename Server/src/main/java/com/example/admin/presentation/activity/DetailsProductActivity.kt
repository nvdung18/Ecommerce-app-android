package com.example.admin.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.admin.R
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.product.ProductEntity
import com.example.admin.databinding.ActivityDetailsProductBinding
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class DetailsProductActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsProductBinding
    private lateinit var productItem:ProductEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_product)

        binding= ActivityDetailsProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        var i=intent
        var productJson=i.getStringExtra("product").toString()

        var gson=Gson() //convert json to object
        val type = object : TypeToken<ProductEntity>() {}.type
        productItem = gson.fromJson(productJson, type)

        // set information
        setInformationProduct()

        binding.imgBtnDetailsProductBack.setOnClickListener {
            back()
        }
    }

    private fun back() {
        var intent=Intent(this,ProductActivity::class.java)
        startActivity(intent)
    }

    private fun setInformationProduct() {
        binding.txtDtpIdProduct.text=productItem.idProduct
        binding.txtDtpBranch.text=productItem.idBranch
        binding.txtDtpNameProduct.text=productItem.nameProduct
        Glide.with(this).load(productItem.image).into(binding.imgDtpProduct)
        binding.txtDtpPrice.text=productItem.price.toString()
        binding.txtDtpDescription.text=productItem.description
        binding.txtDtpSale.text=productItem.sale.toString()
        binding.txtDtpType.text=productItem.type
    }
}