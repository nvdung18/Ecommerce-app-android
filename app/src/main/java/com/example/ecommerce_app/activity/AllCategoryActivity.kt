package com.example.ecommerce_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.adapter.CategoryAdapter
import com.example.ecommerce_app.databinding.ActivityAllCategoryBinding
import com.example.ecommerce_app.models.BranchEntity
import com.example.ecommerce_app.models.BrandAndModel

class AllCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllCategoryBinding
    private lateinit var listCategory: ArrayList<BranchEntity>
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("listCategory")
        listCategory = bundle?.getSerializable("LISTCATEGORY") as ArrayList<BranchEntity>

        categoryAdapter = CategoryAdapter(listCategory, applicationContext)

        binding.allCategorytRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = categoryAdapter
        }

        binding.backImg.setOnClickListener {
            onBackPressed()
        }

        binding.searchTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    categoryAdapter.filter.filter(p0)
                }catch (e: Exception) {

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

}