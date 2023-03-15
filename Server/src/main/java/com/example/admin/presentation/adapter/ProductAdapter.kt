package com.example.admin.presentation.adapter

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.data.model.Product
import kotlinx.android.synthetic.main.single_product.view.*

class ProductAdapter(var list:List<Product>):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.single_product,parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.itemView.apply {
            txtIdProduct.text=list[position].idProduct
            txtNameProduct.text=list[position].nameProduct

            btnDetailsProduct.setOnClickListener{
                Log.e("a",list[position].idProduct.toString())
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}