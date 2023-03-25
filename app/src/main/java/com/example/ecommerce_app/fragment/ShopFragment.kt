package com.example.ecommerce_app.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapter.SaleProductAdapter
import com.example.ecommerce_app.databinding.FragmentShopBinding
import com.example.ecommerce_app.models.BranchEntity
import com.example.ecommerce_app.models.ProductEntity

class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    private lateinit var cateList: ArrayList<BranchEntity>
    private lateinit var coverProduct: ArrayList<ProductEntity>

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

        return binding.root
    }

    private fun setCategoryData() {
        val cursor = activity?.contentResolver?.query(uri_branch, null, null, null, null)

    }

    private fun getAllProductFromDatabase() {
        val cursor = activity?.contentResolver?.query(uri_product,null, null, null, null)
        if(cursor != null) {
            if(cursor != null && cursor.moveToFirst()) {
                do {
                    val product = ProductEntity(
                        cursor.getString(cursor.getColumnIndexOrThrow("idProduct")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nameProduct")),
                        cursor.getString(cursor.getColumnIndexOrThrow("image")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("type")),
                        cursor.getFloat(cursor.getColumnIndexOrThrow("sale")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("soldQuantity")),
                        cursor.getString(cursor.getColumnIndexOrThrow("idBranch"))
                    )
                    coverProduct.add(product)
                } while (cursor.moveToNext())
            }
        }
    }
}