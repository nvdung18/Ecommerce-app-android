package com.example.ecommerce_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ecommerce_app.models.ProductEntity
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.SingleProductBinding

class ProductAdapter: Adapter<ProductAdapter.HolderProduct> {

    private lateinit var context: Context
    private lateinit var listProduct: ArrayList<ProductEntity>

    private lateinit var binding: SingleProductBinding

    constructor(context: Context, listProduct: ArrayList<ProductEntity>) {
        this.context = context
        this.listProduct = listProduct
    }

    inner class HolderProduct(itemView: View): ViewHolder(itemView) {
        val productImage_singleProduct: ImageView = binding.productImageSingleProduct
        val productRating_singleProduct: RatingBar = binding.productRatingSingleProduct
        val productBrandName_singleProduct: TextView = binding.productBrandNameSingleProduct
        val discountTv_singleProduct: TextView = binding.discountTvSingleProduct
        val productName_singleProduct: TextView = binding.productNameSingleProduct
        val productPrice_singleProduct: TextView = binding.productPriceSingleProduct
        val discount_singleProduct = binding.discountSingleProduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderProduct {
        binding = SingleProductBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderProduct(binding.root)
    }

    override fun onBindViewHolder(holder: HolderProduct, position: Int) {
        val model = listProduct[position]
        holder.productBrandName_singleProduct.text = model.idBranch
        holder.productName_singleProduct.text = model.nameProduct
        holder.productPrice_singleProduct.text = "$${model.price}"
        Glide.with(context)
            .load("https://image.freepik.com/free-photo/full-length-shot-glad-curly-woman-striped-pants-jumping-purple-wall-indoor-portrait-wonderful-girl-sunglasses-fooling-around_197531-5125.jpg")
//            .placeholder(R.drawable.bags)
            .into(holder.productImage_singleProduct)

        holder.itemView.setOnClickListener{
            goDetailsPage(model)
        }
    }

    override fun getItemCount(): Int {
      return listProduct.size
    }

    private fun goDetailsPage(model: ProductEntity) {
        Toast.makeText(context, "Not yet data", Toast.LENGTH_SHORT).show()
    }
}