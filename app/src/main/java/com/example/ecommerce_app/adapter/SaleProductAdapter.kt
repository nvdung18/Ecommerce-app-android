package com.example.ecommerce_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.ecommerce_app.models.ProductEntity
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activity.ProductDetailsActivity
import com.example.ecommerce_app.databinding.SingleProductBinding
import com.example.ecommerce_app.models.BrandAndModel

class SaleProductAdapter: Adapter<SaleProductAdapter.HolderProduct> {
    private lateinit var context: Context
    private lateinit var listProduct: ArrayList<BrandAndModel>

    private lateinit var binding: SingleProductBinding

    constructor(context: Context, listProduct: ArrayList<BrandAndModel>) {
        this.context = context
        this.listProduct = listProduct
    }

    inner class HolderProduct(itemView: View): RecyclerView.ViewHolder(itemView) {
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
        holder.productBrandName_singleProduct.text = model.nameBranch
        holder.productName_singleProduct.text = model.nameProduct
        holder.productPrice_singleProduct.text = "$${model.price}"
        Glide.with(context)
            .load(model.image)
            .placeholder(R.drawable.bags)
            .into(holder.productImage_singleProduct)

        holder.itemView.setOnClickListener{
            getDetailsPage(model)
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    private fun getDetailsPage(model: BrandAndModel) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("model", model)
        context.startActivity(intent)
    }
}