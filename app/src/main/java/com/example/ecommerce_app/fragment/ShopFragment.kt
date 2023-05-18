package com.example.ecommerce_app.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activity.AllCategoryActivity
import com.example.ecommerce_app.activity.AllProductActivity
import com.example.ecommerce_app.adapter.CategoryAdapter
import com.example.ecommerce_app.adapter.CoverProductAdapter
import com.example.ecommerce_app.adapter.SaleProductAdapter
import com.example.ecommerce_app.databinding.FragmentShopBinding
import com.example.ecommerce_app.models.BranchEntity
import com.example.ecommerce_app.models.BrandAndModel
import com.example.ecommerce_app.models.ProductEntity

class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    private lateinit var cateList: ArrayList<BranchEntity>
    private lateinit var coverProduct: ArrayList<BrandAndModel>

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var coverProductAdapter: CoverProductAdapter

    val uri_product: Uri = Uri.parse("content://com.example.admin/Product")
    val uri_branch: Uri = Uri.parse("content://com.example.admin/Branch")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopBinding.inflate(LayoutInflater.from(context), container, false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        cateList = arrayListOf()
        coverProduct = arrayListOf()

        getAllProductFromDatabase()
        setCategoryData()

        Log.e("a",coverProduct.toString())

        coverProductAdapter = CoverProductAdapter(activity as Context, coverProduct)


        binding.coverRecViewShopFrag.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = coverProductAdapter
        }

        categoryAdapter = CategoryAdapter(cateList, activity as Context)

        binding.categoriesRecView.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
            setHasFixedSize(true)
            adapter = categoryAdapter
        }

        binding.categoriesGroupViewAll.setOnClickListener {
            val intent = Intent(activity as Context, AllCategoryActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("LISTCATEGORY", cateList)
            intent.putExtra("listCategory", bundle)
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }


        return binding.root
    }

    private fun setCategoryData() {
        val cursor = activity?.contentResolver?.query(uri_branch, null, null, null, null)
        if(cursor != null) {
            if(cursor != null && cursor.moveToFirst()) {
                do {
                    val brandEntity = BranchEntity(
                        cursor.getString(cursor.getColumnIndexOrThrow("idBranch")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nameBranch"))
                    )
                    cateList.add(brandEntity)
                } while (cursor.moveToNext())
            }
        }
    }

    private fun getAllProductFromDatabase() {
        val cursor = activity?.contentResolver?.query(uri_product,null, null, null, null)
        if(cursor != null) {
            if(cursor != null && cursor.moveToFirst()) {
                do {
                    val branch_model = BrandAndModel(
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
                    coverProduct.add(branch_model)
                } while (cursor.moveToNext())
            }
        }
    }
}