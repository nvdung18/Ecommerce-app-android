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
import androidx.recyclerview.widget.LinearLayoutManager
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

class OrderUserAdapter(private val ctx: Context,val orderList:ArrayList<List<OrderAndOrderdetails>>,
                       val productMap: Map<String,BrandAndModel>,val stateProduct:String):Adapter<OrderUserAdapter.OrderUserViewHolder>() {
    private lateinit var binding:RowProductOrderUserBinding
    private lateinit var def:DecimalFormat
    private lateinit var adapter:RowProductOrderUserAdapter
    private var targetOrderList= mutableListOf<ArrayList<OrderAndOrderdetails>>() as ArrayList<List<OrderAndOrderdetails>>
    inner class OrderUserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var txtIdOrderOrderUser=binding.txtIdOrderOrderUser
        var txtStatusOrderUser=binding.txtStatusOrderUser
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
        var orderListItem=targetOrderList[position]
        var anOrder:OrderAndOrderdetails=orderListItem[0]
        var priceOfficial:Double=(anOrder.productMoney-((anOrder.productMoney*anOrder.discountPercent)/100))+anOrder.deliveryCharges
        var allQuantityProduct:Int=0
        for(orderItem in orderListItem){
            allQuantityProduct+=orderItem.quantity
        }

        holder.txtIdOrderOrderUser.text="Id order: ${anOrder.idOrder}"
        holder.txtStatusOrderUser.text=stateProduct
        holder.txtAllQuantityProduct.text=allQuantityProduct.toString()
        holder.txtPriceOfficial.text=def.format(priceOfficial).toString()
        holder.txtDetailsStatusOrderUser.text="Order: ${stateProduct}"

        adapter= RowProductOrderUserAdapter(ctx,
            ArrayList(orderListItem),productMap,stateProduct
        )
        binding.rvListOrderUser.adapter=adapter
        binding.rvListOrderUser.layoutManager= LinearLayoutManager(
            ctx,
            LinearLayoutManager.VERTICAL,
            false
        )

        holder.btnOrderDetails.setOnClickListener {
            val gson=Gson()
            var jsonOrderListItem=gson.toJson(orderListItem)
            var jsonProductMap=gson.toJson(productMap)
//            Log.e("a",jsonOrderListItem)
            var intent=Intent(ctx,OrderDetailActivity::class.java)
            intent.putExtra("jsonOrderListItem",jsonOrderListItem)
            intent.putExtra("jsonProductMap",jsonProductMap)
            intent.putExtra("stateOrder",stateProduct)
            ContextCompat.startActivity(ctx,intent, Bundle.EMPTY)
        }
    }
}