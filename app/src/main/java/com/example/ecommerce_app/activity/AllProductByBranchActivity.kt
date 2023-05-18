package com.example.ecommerce_app.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapter.VisualResultAdapter
import com.example.ecommerce_app.databinding.ActivityAllProductBinding
import com.example.ecommerce_app.databinding.ActivityAllProductByBranchBinding
import com.example.ecommerce_app.models.BrandAndModel

class AllProductByBranchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllProductByBranchBinding
    private lateinit var listProduct: ArrayList<BrandAndModel>
    lateinit var productAdapter: VisualResultAdapter
    val uri_product: Uri = Uri.parse("content://com.example.admin/Product")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductByBranchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idBranch = intent.getStringExtra("idBranch").toString()
        val nameBranch = intent.getStringExtra("nameBranch").toString()

        binding.backImg.setOnClickListener {
            onBackPressed()
        }

        listProduct = ArrayList()

        binding.branchTv.text = nameBranch.toString()
        getListProductByBranch(idBranch)

        productAdapter = VisualResultAdapter(applicationContext, listProduct)


        binding.allProductRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = productAdapter
        }

        binding.searchTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    productAdapter.filter.filter(s)
                }catch (e : Exception) {

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun getListProductByBranch(idBranch: String) {
        val cursor = contentResolver?.query(uri_product, null, "idBranch = ?", arrayOf(idBranch), null)
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
                    listProduct.add(product)
                }while (cursor.moveToNext())
            }
        }
    }
}