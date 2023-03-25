package com.example.ecommerce_app.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ecommerce_app.models.ProductEntity

import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.CoverSingleBinding

class CoverProductAdapter:Adapter<CoverProductAdapter.HolderCoverProduct> {

    lateinit var binding: CoverSingleBinding
    private var coverProductList: ArrayList<ProductEntity>
    private var context: Context

    constructor(context: Context, coverProductList: ArrayList<ProductEntity>) {
        this.context = context
        this.coverProductList = coverProductList
    }

    inner class HolderCoverProduct(itemView: View): ViewHolder(itemView) {
        var productImage_coverPage = binding.productImageCoverPage
        var productNoteCover = binding.productNoteCover
        var productCheck_coverPage = binding.productCheckCoverPage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCoverProduct {
        binding= CoverSingleBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderCoverProduct(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCoverProduct, position: Int) {
        val model = coverProductList[position]
        val idProduct = model.idProduct
        val nameProduct = model.nameProduct
        val image = model.image
        val price = model.price
        val description = model.description
        val type = model.type
        val soldQuantity = model.soldQuantity
        val idBranch = model.idBranch

        Log.d("CHECK", "${image}")


        // use this with URL
        Glide.with(context)
            .load(image)
            .into(holder.productImage_coverPage)
//        holder.productImage_coverPage.setImageDrawable(R.drawable.bags)
//        val drawable: Drawable? = binding.productImageCoverPage.resources.getDrawable(com.example.admin.R.drawable.chart)
//        holder.productImage_coverPage.setImageDrawable(drawable)
        holder.productNoteCover.text = nameProduct
        holder.productCheck_coverPage.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return coverProductList.size
    }
}