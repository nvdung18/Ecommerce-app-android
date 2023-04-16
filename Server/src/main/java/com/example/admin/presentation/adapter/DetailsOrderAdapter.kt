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
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.detailsOrder.OrderDetailsEntity
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class DetailsOrderAdapter(private val ctx:Context,private val detailsOrderList:List<OrderDetailsEntity>):RecyclerView.Adapter<DetailsOrderAdapter.DetailsOrderViewHolder>() {
    private val detailsOrderArrayList:List<OrderDetailsEntity> = detailsOrderList
    private lateinit var def:DecimalFormat

    inner class DetailsOrderViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){

        val txtNameProductDtOrder=itemview.findViewById<TextView>(R.id.txtNameProductDtOrder)
        val imgProductDtOrder=itemview.findViewById<ImageView>(R.id.imgProductDtOrder)
        val txtAmountOfProduct=itemview.findViewById<TextView>(R.id.txtAmountOfProduct)
        val txtUnitPriceProduct=itemview.findViewById<TextView>(R.id.txtUnitPriceProduct)
        val txtTotalPriceProduct=itemview.findViewById<TextView>(R.id.txtTotalPriceProduct)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsOrderViewHolder {
        val view=LayoutInflater.from(ctx).inflate(R.layout.single_details_order,parent,false)
        return DetailsOrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return detailsOrderList.size
    }

    override fun onBindViewHolder(holder: DetailsOrderViewHolder, position: Int) {
        val detailsOrderItem=detailsOrderList[position]

        def = DecimalFormat("#,###.###")//use to format number like this: 100.000
        def.decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }

        val idProduct=detailsOrderItem.idProduct //get idProduct to get Information of product
        var instance= AppDatabase.getInstance(ctx)
        var productItem=instance.productDao().getProductByIDServer(idProduct)

        holder.txtNameProductDtOrder.text=productItem.nameProduct
        Glide.with(ctx)
            .load(productItem.image)
            .into(holder.imgProductDtOrder)
        holder.txtAmountOfProduct.text=detailsOrderItem.quantity.toString()
        holder.txtUnitPriceProduct.text=def.format(productItem.price).toString()
        holder.txtTotalPriceProduct.text=def.format(detailsOrderItem.total).toString()
    }
}