package com.example.admin.presentation.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.admin.R
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.product.ProductEntity
import com.example.admin.data.room.product.ProductViewModel
import com.example.admin.databinding.ActivityAddProductBinding
import com.example.admin.databinding.ActivityEditProductBinding
import com.google.common.reflect.TypeToken
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class EditProduct : AppCompatActivity() {
    private lateinit var binding:ActivityEditProductBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var productItem:ProductEntity
    private lateinit var bindingInclude: ActivityAddProductBinding
    private lateinit var def:DecimalFormat

    private var listBranch= mutableListOf<BranchEntity>()
    private var listNameBranch= mutableListOf<String>()
    private lateinit var imgUri: Uri
    private lateinit var storageReference: StorageReference
    private var checkImgChange=0 //if checkImgChange==0, it's mean the image has been loaded from db and it is default img, if checkImgChange==1 the img be changed.
                                // So if img change we will add it into firebase store

    private lateinit var branchSelected:String // when we choose a branch from spinner, we save it in this variable for SubmitAddProduct func when we add product
    // (we will save idBranch)
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        binding= ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingInclude=binding.includedLayout
//        Change some information to corresponding with the interface
        bindingInclude.btnSubmitAddProduct.text="Update"

        viewModel= ViewModelProviders.of(this).get(ProductViewModel::class.java)

        lifecycleScope.launch {
            viewModel.allBranch.observe(this@EditProduct, {List->
                List?.let {
                    for (item in it){
                        listBranch.add(item)
                    }
                }
                initComponents()
            })
        }
    }

    private fun initComponents() {
        var i=intent
        var productJson=i.getStringExtra("product")

        var gson= Gson() //convert json to object
        val type = object : TypeToken<ProductEntity>() {}.type
        productItem = gson.fromJson(productJson, type)

        setInforProduct()

        bindingInclude.imgBtnAddProductBack.setOnClickListener {
            back()
        }

        bindingInclude.btnSubmitAddProduct.setOnClickListener {
            SubmitUpdateProduct()
        }

        bindingInclude.btnSelectImgProduct.setOnClickListener {
            SelectImgProduct()
        }


        bindingInclude.btnSelectImgProductDefault.setOnClickListener {
            checkImgChange=0;
            Glide.with(this).load(productItem.image).into(bindingInclude.imgSelectedProduct)
            Log.e("check",checkImgChange.toString())
        }
    }

    private fun SubmitUpdateProduct() {
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Update Product......")
        progressDialog.show()

        var product=productItem
        val idProduct=productItem.idProduct
        val branchSelected = branchSelected
        val nameProduct = bindingInclude.edtNameProduct.text.toString()
        val priceProduct = bindingInclude.edtPriceProduct.text.toString().toDouble()
        val descriptionProduct = bindingInclude.edtDescriptionProduct.text.toString()
        val typeOfProduct = bindingInclude.edtTypeOfProduct.text.toString()
        val saleOfProduct = bindingInclude.edtSaleOfProduct.text.toString().toFloat()
        if(checkImgChange==0){
            product = ProductEntity(idProduct, nameProduct, productItem.image, priceProduct, descriptionProduct, typeOfProduct, saleOfProduct, 0, branchSelected)
            viewModel.updateProduct(product)
            runOnUiThread {
                Toast.makeText(this@EditProduct," Update Product Successful", Toast.LENGTH_SHORT).show()
            }
            val i=Intent(this@EditProduct,ProductActivity::class.java)
            startActivity(i)
            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }
        }else{
            var imgUrl=""
            var AddProductActivity=AddProductActivity()
            AddProductActivity.uploadImg(imgUri){ downloadUrl ->
                Log.e("imgUrl", downloadUrl)
                imgUrl=downloadUrl

//
                val branchSelected = branchSelected
                val nameProduct = bindingInclude.edtNameProduct.text.toString()
                val priceProduct = bindingInclude.edtPriceProduct.text.toString().toDouble()
                val descriptionProduct = bindingInclude.edtDescriptionProduct.text.toString()
                val typeOfProduct = bindingInclude.edtTypeOfProduct.text.toString()
                val saleOfProduct = bindingInclude.edtSaleOfProduct.text.toString().toFloat()

                product = ProductEntity(idProduct, nameProduct, imgUrl, priceProduct, descriptionProduct, typeOfProduct, saleOfProduct, 0, branchSelected)
                // Set notification after we add new product
                Log.e("Product", product.toString())
                viewModel.updateProduct(product)
                runOnUiThread {
                    Toast.makeText(this@EditProduct," Update Product Successful", Toast.LENGTH_SHORT).show()
                }
                val i=Intent(this@EditProduct,ProductActivity::class.java)
                startActivity(i)
                if(progressDialog.isShowing){
                    progressDialog.dismiss()
                }
            }
        }


    }

    private fun SelectImgProduct() {
        var intent: Intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100 && data!=null&& data.data!=null){
            imgUri= data.data!!
            bindingInclude.imgSelectedProduct.setImageURI(imgUri)
            checkImgChange=1
            bindingInclude.btnSelectImgProductDefault.visibility=View.VISIBLE

            Log.e("check",checkImgChange.toString())
        }
    }

    private fun setInforProduct() {
        def = DecimalFormat("#,###.###")//use to format number like this: 100.000
        def.decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }

        //        set branch for spinner
        for (item in listBranch) {
            listNameBranch.add(item.nameBranch)
        }
        val spinnerAdt= ArrayAdapter(this,android.R.layout.simple_spinner_item,listNameBranch)
        bindingInclude.spBranch.adapter=spinnerAdt

//        set branch of product for spinner
        var posBranch=0
        for ((index, branch) in listBranch.withIndex()) {
            if (branch.idBranch == productItem.idBranch) {
                posBranch = index
                break
            }
        }
        bindingInclude.spBranch.setSelection(posBranch)

//        onItemSelectedListener of spinner
        bindingInclude.spBranch.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                branchSelected=listBranch[position].idBranch
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                branchSelected=listBranch[0].idBranch
            }
        }

        bindingInclude.edtNameProduct.setText(productItem.nameProduct)
        bindingInclude.edtDescriptionProduct.setText(productItem.description)
        Glide.with(this).load(productItem.image).into(bindingInclude.imgSelectedProduct)
        bindingInclude.edtPriceProduct.setText(def.format(productItem.price).toString())
        bindingInclude.edtTypeOfProduct.setText(productItem.type)
        bindingInclude.edtSaleOfProduct.setText(productItem.sale.toString())
    }

    private fun back() {
        var intent= Intent(this,ProductActivity::class.java)
        startActivity(intent)
    }
}