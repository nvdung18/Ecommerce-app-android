package com.example.ecommerce_app.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.ecommerce_app.activity.OrderDetailActivity
import com.example.ecommerce_app.databinding.RowProductOrderUserBinding
import com.example.ecommerce_app.models.BrandAndModel
import com.example.ecommerce_app.models.OrderAndOrderdetails
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class OrderUserAdapter(private val ctx: Context,val orderList:ArrayList<OrderAndOrderdetails>,
                       val productMap: Map<String,BrandAndModel>,val stateProduct:String):Adapter<OrderUserAdapter.OrderUserViewHolder>() {
    private lateinit var binding:RowProductOrderUserBinding
    private lateinit var def:DecimalFormat
    private var targetOrderList= mutableListOf<OrderAndOrderdetails>()
    inner class OrderUserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var txtIdOrderOrderUser=binding.txtIdOrderOrderUser
        var imgProductOrderUser=binding.imgProductOrderUser
        var txtNameProductOrderUser=binding.txtNameProductOrderUser
        var txtBranchOrderUser=binding.txtBranchOrderUser
        var txtStatusOrderUser=binding.txtStatusOrderUser
        var txtQuantityAProduct=binding.txtQuantityAProduct
        var txtPriceAProduct=binding.txtPriceAProduct
        var txtAllQuantityProduct=binding.txtAllQuantityProduct
        var txtPriceOfficial=binding.txtPriceOfficial
        var txtDetailsStatusOrderUser=binding.txtDetailsStatusOrderUser
        var btnOrderDetails=binding.txtDetailsOrder
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderUserViewHolder {
        targetOrderList.clear()
        targetOrderList.addAll(orderList)
        def = DecimalFormat("#,###.###")//use to format number like this: 100.000
        def.decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }
        binding= RowProductOrderUserBinding.inflate(LayoutInflater.from(ctx),parent,false)
        return OrderUserViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderUserViewHolder, position: Int) {
        var orderItem=targetOrderList[position]
        var product=productMap.get(orderItem.idProduct)
        holder.txtIdOrderOrderUser.text="Id order: ${orderItem.idOrder}"
        Glide.with(ctx).load(product!!.image).into(holder.imgProductOrderUser)
        holder.txtNameProductOrderUser.text=product.nameProduct
        holder.txtBranchOrderUser.text=product.nameBranch
        holder.txtStatusOrderUser.text=stateProduct
        holder.txtQuantityAProduct.text="1"
        holder.txtPriceAProduct.text=def.format(product.price).toString()
        holder.txtAllQuantityProduct.text=orderItem.quantity.toString()
        holder.txtPriceOfficial.text=def.format((orderItem.quantity*product.price).toDouble()).toString()
        holder.txtDetailsStatusOrderUser.text="Order ${stateProduct}"

        holder.btnOrderDetails.setOnClickListener {
            val gson=Gson()
            var jsonListOrder=gson.toJson(orderItem)
            var intent=Intent(ctx,OrderDetailActivity::class.java)
            ContextCompat.startActivity(ctx,intent, Bundle.EMPTY)
        }
    }
}