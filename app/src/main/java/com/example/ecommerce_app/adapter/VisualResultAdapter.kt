package com.example.ecommerce_app.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activity.ProductDetailsActivity
import com.example.ecommerce_app.databinding.PredictedResultSingleBinding
import com.example.ecommerce_app.filter.FilterCategory
import com.example.ecommerce_app.filter.FilterProduct
import com.example.ecommerce_app.models.BranchEntity
import com.example.ecommerce_app.models.BrandAndModel
import com.example.ecommerce_app.models.ProductEntity
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class VisualResultAdapter: Adapter<VisualResultAdapter.VisualViewHolder>, Filterable {
    private lateinit var binding: PredictedResultSingleBinding
    private lateinit var context: Context
    lateinit var listProduct: ArrayList<BrandAndModel>
    private var filter: FilterProduct? = null
    private lateinit var filterList:  ArrayList<BrandAndModel>

    constructor(context: Context, listProduct: ArrayList<BrandAndModel>) {
        this.context = context
        this.listProduct = listProduct
        this.filterList = listProduct
    }

    inner class VisualViewHolder(itemView: View): ViewHolder(itemView) {
        val productImage_singleProduct: ImageView = binding.productImagePredictProduct
        val productBrandName_singleProduct: TextView = binding.productBrandNamePredictProduct
        val productName_singleProduct: TextView = binding.productNamePredictProduct
        val productPrice_singleProduct: TextView = binding.productPricePredictProduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisualViewHolder {
        binding = PredictedResultSingleBinding.inflate(LayoutInflater.from(context), parent, false)
        return VisualViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: VisualViewHolder, position: Int) {
        val model = listProduct[position]
        Glide.with(context)
            .load(model.image)
            .placeholder(R.drawable.loading)
            .into(holder.productImage_singleProduct)

        holder.productBrandName_singleProduct.text = model.nameBranch
        holder.productName_singleProduct.text = model.nameProduct
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        formatter.currency = Currency.getInstance("VND")
        val formattedPrice = formatter.format(model.price)
        holder.productPrice_singleProduct.text = "$formattedPrice"

        holder.itemView.setOnClickListener{
            goDetailsPage(model)
        }
    }

    private fun goDetailsPage(model: BrandAndModel) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("model", model)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK // add this line
        context?.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun getFilter(): Filter {
        if(filter == null) {
            filter = FilterProduct(filterList,this)
        }
        return filter as FilterProduct
    }
}