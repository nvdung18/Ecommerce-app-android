package com.example.admin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.data.model.Product
import com.example.admin.presentation.activity.ProductDetailsActivity

class ProductAdapter(var list: MutableList<Product>):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.single_product,parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.itemView.apply {
//            txtIdProduct.text=list[position].idProduct
//            txtNameProduct.text=list[position].nameProduct
//
//            btnDetailsProduct.setOnClickListener{
//                val i= Intent(context,ProductDetailsActivity::class.java)
//                context.startActivity(i)
//            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}