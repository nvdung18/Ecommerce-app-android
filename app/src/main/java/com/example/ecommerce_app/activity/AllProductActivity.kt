package com.example.ecommerce_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapter.CoverProductAdapter
import com.example.ecommerce_app.adapter.VisualResultAdapter
import com.example.ecommerce_app.databinding.ActivityAllProductBinding
import com.example.ecommerce_app.models.BrandAndModel
import com.example.ecommerce_app.models.ProductEntity

class AllProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllProductBinding
    private lateinit var listProduct: ArrayList<BrandAndModel>
    lateinit var productAdapter: VisualResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("coverProduct")
        listProduct = bundle?.getSerializable("ARRAYLIST") as ArrayList<BrandAndModel>

        productAdapter = VisualResultAdapter(applicationContext, listProduct)

        binding.allProductRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = productAdapter
        }

        binding.backImg.setOnClickListener {
            onBackPressed()
        }

        binding.searchTv.addTextChangedListener(object : TextWatcher{
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
}