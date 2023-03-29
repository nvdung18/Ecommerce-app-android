package com.example.admin.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin.R
import com.example.admin.data.model.Product
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.product.ProductEntity
import com.example.admin.presentation.activity.ProductDetailsActivity

class ProductAdapter(private val ctx: Context):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val productList:ArrayList<ProductEntity> = arrayListOf()
    inner class ProductViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val txtIdProduct=itemView.findViewById<TextView>(R.id.txtIdProduct)
        val txtNameProduct=itemView.findViewById<TextView>(R.id.txtNameProduct)
        val imgSigleProduct=itemView.findViewById<ImageView>(R.id.imgSigleProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.single_product,parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productItem:ProductEntity=productList[position]
        holder.txtIdProduct.text=productItem.idProduct
        holder.txtNameProduct.text=productItem.nameProduct
        Glide.with(ctx)
            .load(productItem.image)
            .into(holder.imgSigleProduct)
//        holder.itemView.apply {
////            txtIdProduct.text=list[position].idProduct
////            txtNameProduct.text=list[position].nameProduct
////
////            btnDetailsProduct.setOnClickListener{
////                val i= Intent(context,ProductDetailsActivity::class.java)
////                context.startActivity(i)
////            }
//        }



    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateList(newList: List<ProductEntity>){
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }
}