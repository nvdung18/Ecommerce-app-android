package com.example.ecommerce_app.activity

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapter.ProductAdapter
import com.example.ecommerce_app.databinding.ActivityProductDetailsBinding
import com.example.ecommerce_app.models.BrandAndModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    val uri_product: Uri = Uri.parse("content://com.example.admin/Product")
    val uri_cartDetails: Uri = Uri.parse("content://com.example.admin/CartDetails")
    val uri_account: Uri = Uri.parse("content://com.example.admin/account")
    val uri_cart: Uri = Uri.parse("content://com.example.admin/Cart")
    private lateinit var productAdapter: ProductAdapter
    private lateinit var listProductRecom: ArrayList<BrandAndModel>
    var qua: Int = 1
    var pPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent().getParcelableExtra<BrandAndModel>("model")

        setProductData(intent)
        listProductRecom = ArrayList()
        getAllProductFromDatabase()
        productAdapter = ProductAdapter(this@ProductDetailsActivity, listProductRecom)

        binding.RecomRecViewProductDetailsPage.apply {
            layoutManager = LinearLayoutManager(
                this@ProductDetailsActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            setHasFixedSize(true)
            adapter = productAdapter
        }

        binding.backIvProfileFrag.setOnClickListener {
            onBackPressed()
        }

        binding.addToCartProductDetailsPage.setOnClickListener {

            val bottomSheetDialod = BottomSheetDialog(
                this, R.style.BottomSheetDialogTheme
            )

            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.fragment_add_to_bag,
                findViewById<ConstraintLayout>(R.id.bottomSheet)
            )

            bottomSheetView.findViewById<View>(R.id.addToCart_BottomSheet).setOnClickListener {

                pPrice *= bottomSheetView.findViewById<EditText>(R.id.quantityEtBottom).text.toString()
                    .toInt()
                addProductToBag(intent)
                bottomSheetDialod.dismiss()
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.minusLayout).setOnClickListener {
                if(bottomSheetView.findViewById<EditText>(R.id.quantityEtBottom).text.toString()
                        .toInt() > 1){
                    qua--
                    bottomSheetView.findViewById<EditText>(R.id.quantityEtBottom).setText(qua.toString())
                }
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.plusLayout).setOnClickListener {
                    qua++
                    bottomSheetView.findViewById<EditText>(R.id.quantityEtBottom).setText(qua.toString())
            }

            bottomSheetDialod.setContentView(bottomSheetView)
            bottomSheetDialod.show()
        }
    }

    private fun addProductToBag(model: BrandAndModel?) {
        // the first check the product in cart is exist
        // if(true) update quantity
        // false -> create an cartDetailsEntity
        insertProduct_CartDetails(model)
    }

    private fun setProductData(intent: BrandAndModel?) {
        binding.productNameProductDetailsPage.text = intent?.nameProduct
        binding.productPriceProductDetailsPage.text = intent?.price?.toDouble().toString()
        binding.productBrandProductDetailsPage.text = intent?.nameBranch
        binding.productDesProductDetailsPage.text = intent?.description
        Glide.with(applicationContext)
            .load(intent?.image)
            .into(binding.productImageProductDetailsPage)
    }

    private fun getAllProductFromDatabase() {
        val cursor = contentResolver?.query(uri_product,null, null, null, null)
        if(cursor != null) {
            if(cursor != null && cursor.moveToFirst()) {
                do {
                    val product = BrandAndModel(
                        cursor.getString(cursor.getColumnIndexOrThrow("idProduct")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nameProduct")),
                        cursor.getString(cursor.getColumnIndexOrThrow("image")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("type")),
                        cursor.getFloat(cursor.getColumnIndexOrThrow("sale")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("soldQuantity")),
                        cursor.getString(cursor.getColumnIndexOrThrow("idBranch")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nameBranch"))
                    )
                    listProductRecom.add(product)
                } while (cursor.moveToNext())
            }
        }
    }

    private fun insertProduct_CartDetails(model: BrandAndModel?) {
        /*
            val quantity = values?.getAsString("quantity")!!.toInt()
                val idCart = values?.getAsString("idCart").toString()
                val idProduct = values?.getAsString("idProduct").toString()
         */

        val values = ContentValues().apply {
            put("quantity", qua)
            put("idCart", getcurrentIdCart())
            put("idProduct", model?.idProduct)
        }
        val cartDetailsEntity = contentResolver?.insert(uri_cartDetails, values)
        Toast.makeText(this@ProductDetailsActivity, "Success to your bag", Toast.LENGTH_SHORT).show()
    }

    private fun getcurrentIdAccount(): String? {
        val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        val cursor = contentResolver?.query(uri_account, null, "token = ?", arrayOf(token), null)
        if(cursor != null) {
            var idAccount = ""
            if (cursor != null && cursor.moveToFirst()) {
                idAccount = cursor.getString(cursor.getColumnIndexOrThrow("idAccount"))
            }
            if(cursor != null) {
                cursor.close()
            }
            return idAccount
        }
        return null
    }

    private fun getcurrentIdCart(): String? {
        val idAccount = getcurrentIdAccount()
        val cursor = contentResolver?.query(uri_cart, null, "idAccount = ?", arrayOf(idAccount), null)
        if(cursor != null) {
            var idCart = ""
            if(cursor != null && cursor.moveToFirst()) {
                idCart  = cursor.getString(cursor.getColumnIndexOrThrow("idCart"))
            }
            if(cursor != null) {
                cursor.close()
            }
            return idCart
        }
        return null
    }
}