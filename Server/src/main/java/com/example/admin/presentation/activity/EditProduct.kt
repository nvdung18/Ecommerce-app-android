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

class EditProduct : AppCompatActivity() {
    private lateinit var binding:ActivityEditProductBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var productItem:ProductEntity
    private lateinit var bindingInclude: ActivityAddProductBinding

    private var listBranch= mutableListOf<BranchEntity>()
    private var listNameBranch= mutableListOf<String>()
    private lateinit var imgUri: Uri
    private lateinit var storageReference: StorageReference
    private var checkImgChange=0 //if checkImgChange==0, it's mean the image has been loaded from db => not change

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

        var currentDrawable: Drawable? = bindingInclude.imgSelectedProduct.drawable
        bindingInclude.imgSelectedProduct.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            val newDrawable = bindingInclude.imgSelectedProduct.drawable
            if (newDrawable != null && newDrawable != currentDrawable) {
                // The image has changed
                // Check if the new image is the same as the old image
                if (newDrawable.constantState == currentDrawable?.constantState) {
                    Log.e("a","Same")
                    // The new image is the same as the old image
                } else {
                    // The new image is different from the old image
                    Log.e("a","new")
                }

                // Update the currentDrawable variable to the new Drawable
                currentDrawable = newDrawable
            }
        }

    }

    private fun SubmitUpdateProduct() {
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Add new Product......")
        progressDialog.show()


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
        }
    }

    private fun setInforProduct() {
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
        bindingInclude.edtPriceProduct.setText(productItem.price.toString())
        bindingInclude.edtTypeOfProduct.setText(productItem.type)
        bindingInclude.edtSaleOfProduct.setText(productItem.sale.toString())
    }

    private fun back() {
        var intent= Intent(this,ProductActivity::class.java)
        startActivity(intent)
    }
}