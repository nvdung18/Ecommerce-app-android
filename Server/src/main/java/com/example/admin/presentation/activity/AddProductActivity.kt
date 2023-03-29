package com.example.admin.presentation.activity

import android.app.ProgressDialog
import android.content.Intent
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
import com.example.admin.data.room.branch.BranchViewModel
import com.example.admin.data.room.product.ProductEntity
import com.example.admin.data.room.product.ProductViewModel
import com.example.admin.databinding.ActivityAddProductBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Double
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule


class AddProductActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddProductBinding
    private lateinit var viewModel: ProductViewModel
    private var listBranch= mutableListOf<BranchEntity>()
    private var listNameBranch= mutableListOf<String>()
    private lateinit var imgUri: Uri
    private lateinit var storageReference: StorageReference

    private lateinit var branchSelected:String // when we choose a branch from spinner, we save it in this variable for SubmitAddProduct func when we add product
                                                // (we will save idBranch)
    private lateinit var progressDialog:ProgressDialog

//    data from ProductActivity and new id for new product
    private var latestIdProduct:String=""
    private var sizeOfListProduct:Int=0 //to get size of List Product
    private var newIdProduct:String=""//save new id when we run fun createNewIdProduct
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        binding= ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProviders.of(this).get(ProductViewModel::class.java)


        lifecycleScope.launch {
            viewModel.allBranch.observe(this@AddProductActivity, {List->
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
//        set branch for spinner
        for (item in listBranch) {
            listNameBranch.add(item.nameBranch)
        }
        val spinnerAdt=ArrayAdapter(this,android.R.layout.simple_spinner_item,listNameBranch)
        binding.spBranch.adapter=spinnerAdt

//        onItemSelectedListener of spinner
        binding.spBranch.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
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
//      get data from ProductActivity to set Id for new Product and create new id for product
        val i=intent
        latestIdProduct=i.getStringExtra("latestIdProduct").toString()
        sizeOfListProduct=i.getIntExtra("sizeOfListProduct",0)
        newIdProduct=latestIdProduct
        if(sizeOfListProduct!=0){
            createNewIDProduct(latestIdProduct)
        }


//        Event listener
        binding.btnSubmitAddProduct.setOnClickListener {
            SubmitAddProduct()
        }

        binding.btnSelectImgProduct.setOnClickListener {
            selectImgProduct()
        }
    }

//    Create new Id for product
    private fun createNewIDProduct(latestIdProduct: String) {
//        split id to get number of id
    val numPart=latestIdProduct.split("SP")

//        create new id, if sizeOfListProduct==0, we don't need to create new ID because db product is null, so we just need add into db
    var newNum=numPart[1].toInt()+1
    if(newNum<10 && sizeOfListProduct!=0){
        newIdProduct="SP0"+newNum.toString()
    }else if(newNum>=10 && sizeOfListProduct!=0){
        newIdProduct="SP"+newNum.toString()
    }
    }

//    Selected Img for Product
    private fun selectImgProduct() {
        var intent: Intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,100)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100 && data!=null&& data.data!=null){
            imgUri= data.data!!
            binding.imgSelectedProduct.setImageURI(imgUri)
        }
    }

//    Add product func
    private fun SubmitAddProduct(){

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Add new Product......")
        progressDialog.show()

    var imgUrl=""
    uploadImg { downloadUrl ->
        Log.e("imgUrl", downloadUrl)
        imgUrl=downloadUrl
    }

    Timer().schedule(4000){
        val branchSelected = branchSelected
        val nameProduct = binding.edtNameProduct.text.toString()
        val priceProduct = binding.edtPriceProduct.text.toString().toDouble()
        val descriptionProduct = binding.edtDescriptionProduct.text.toString()
        val typeOfProduct = binding.edtTypeOfProduct.text.toString()
        val saleOfProduct = binding.edtSaleOfProduct.text.toString().toFloat()

        val product = ProductEntity(newIdProduct, nameProduct, imgUrl, priceProduct, descriptionProduct, typeOfProduct, saleOfProduct, 0, branchSelected)
        // Set notification after we add new product
        Log.e("Prouct", product.toString())
        viewModel.insert(product)
        runOnUiThread {
            Toast.makeText(this@AddProductActivity," Add Product Successful", Toast.LENGTH_SHORT).show()
        }
        val i=Intent(this@AddProductActivity,ProductActivity::class.java)
        startActivity(i)
        if(progressDialog.isShowing){
            progressDialog.dismiss()
        }


    }



    }

//    Upload img into firebase
    private fun uploadImg(callback: (String) -> Unit) {
        var downloadUrl =""

        var formatter = SimpleDateFormat( "yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        var now = Date();
        var fileName=formatter.format(now)

        storageReference= FirebaseStorage.getInstance().getReference("image product/"+fileName)
        storageReference.putFile(imgUri).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                // Use the download URL
                downloadUrl = uri.toString()
//                Call back
                callback(downloadUrl)
            }

        }.addOnFailureListener{
            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }
            Toast.makeText(this,"Upload image Failed", Toast.LENGTH_SHORT).show()
        }
    }

}