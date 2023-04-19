package com.example.ecommerce_app.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce_app.activity.OrderDetailActivity
import com.example.ecommerce_app.databinding.RowProductOrderUserBinding
import com.example.ecommerce_app.databinding.SingleRowProductInOrderUserBinding
import com.example.ecommerce_app.models.BrandAndModel
import com.example.ecommerce_app.models.OrderAndOrderdetails
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class RowProductOrderUserAdapter(private val ctx: Context, val orderList:ArrayList<OrderAndOrderdetails>,
                                 val productMap: Map<String, BrandAndModel>, val stateProduct:String):
    RecyclerView.Adapter<RowProductOrderUserAdapter.RowProductOrderUserViewHolder>() {
    private lateinit var binding: SingleRowProductInOrderUserBinding
    private lateinit var def: DecimalFormat
    private var targetOrderList= mutableListOf<OrderAndOrderdetails>()
    inner class RowProductOrderUserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imgProductOrderUser=binding.imgProductOrderUser
        var txtNameProductOrderUser=binding.txtNameProductOrderUser
        var txtBranchOrderUser=binding.txtBranchOrderUser
        var txtQuantityAProduct=binding.txtQuantityAProduct
        var txtPriceAProduct=binding.txtPriceAProduct
        var txtAllQuantityProduct=binding.txtAllQuantityProduct
        var txtPriceOfficial=binding.txtPriceOfficial
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowProductOrderUserViewHolder {
        targetOrderList.clear()
        targetOrderList.addAll(orderList)
        def = DecimalFormat("#,###.###")//use to format number like this: 100.000
        def.decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }
        binding= SingleRowProductInOrderUserBinding.inflate(LayoutInflater.from(ctx),parent,false)
        return RowProductOrderUserViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: RowProductOrderUserViewHolder, position: Int) {
        var orderItem=targetOrderList[position]
        Log.e("order",orderItem.toString())
        var product=productMap.get(orderItem.idProduct)
        Glide.with(ctx).load(product!!.image).into(holder.imgProductOrderUser)
        holder.txtNameProductOrderUser.text=product.nameProduct
        holder.txtBranchOrderUser.text=product.nameBranch
        holder.txtQuantityAProduct.text="1"
        holder.txtPriceAProduct.text=def.format(product.price).toString()
        holder.txtAllQuantityProduct.text=orderItem.quantity.toString()
        holder.txtPriceOfficial.text=def.format((orderItem.quantity*product.price).toDouble()).toString()

    }
}