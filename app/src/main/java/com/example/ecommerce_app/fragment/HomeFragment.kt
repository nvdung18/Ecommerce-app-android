package com.example.ecommerce_app.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.models.ProductEntity
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activity.VisualSearchActivity
import com.example.ecommerce_app.adapter.CoverProductAdapter
import com.example.ecommerce_app.adapter.ProductAdapter
import com.example.ecommerce_app.adapter.SaleProductAdapter
import com.example.ecommerce_app.databinding.FragmentHomeBinding
import java.lang.reflect.Type


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    val TAG = "HOMEFRAGMENT"

    val uri_product: Uri = Uri.parse("content://com.example.admin/Product")

    lateinit var coverProductAdapter: CoverProductAdapter
    lateinit var newProductAdapter: ProductAdapter
    lateinit var saleProductAdapter: SaleProductAdapter

    lateinit var coverProduct: ArrayList<ProductEntity>
    lateinit var newProduct: ArrayList<ProductEntity>
    lateinit var saleProduct: ArrayList<ProductEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context), container, false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        coverProduct = arrayListOf()
        newProduct = arrayListOf()
        saleProduct = arrayListOf()

        getAllProductFromDatabase()

        coverProductAdapter = CoverProductAdapter(activity as Context, coverProduct)
        binding.coverRecView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = coverProductAdapter
        }

        newProductAdapter = ProductAdapter(activity as Context, newProduct)
        binding.newRecView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = newProductAdapter
        }

        saleProductAdapter = SaleProductAdapter(activity as Context, saleProduct)
        binding.saleRecView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = saleProductAdapter
        }

        binding.visualSearchBtnHomePage.setOnClickListener {
            startActivity(Intent(context, VisualSearchActivity::class.java))
            activity!!.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        return binding.root
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
                    newProduct.add(product)
                    saleProduct.add(product)
                } while (cursor.moveToNext())
            }
        }
    }
}