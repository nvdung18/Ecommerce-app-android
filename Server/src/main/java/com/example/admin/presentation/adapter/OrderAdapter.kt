package com.example.admin.presentation.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.data.room.order.OrderEntity
import com.example.admin.databinding.SingleOrderBinding
import com.example.admin.presentation.activity.DetailsOrderActivity
import com.google.firestore.v1.StructuredQuery.Order
import org.w3c.dom.Text

class OrderAdapter(private val ctx: Context,private val funActivity:String): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val orderList:ArrayList<OrderEntity> = arrayListOf()

    inner class OrderViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val txtIdOrder=itemView.findViewById<TextView>(R.id.txtIdOrder)
        val txtIdAccount=itemView.findViewById<TextView>(R.id.txtIdAccount)
        val btnDetailsOrder=itemView.findViewById<Button>(R.id.btnDetailsOrder)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.single_order,parent,false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val orderItem:OrderEntity=orderList[position]
        holder.txtIdOrder.text=orderItem.idOrder
        holder.txtIdAccount.text=orderItem.idAccount

        holder.btnDetailsOrder.setOnClickListener {
            detailsOrderActivity(orderItem)
        }
    }

    private fun detailsOrderActivity(orderItem:OrderEntity) {
        var intent=Intent(ctx,DetailsOrderActivity::class.java)
        intent.putExtra("idOrder",orderItem.idOrder)
        intent.putExtra("fun",funActivity)
        ContextCompat.startActivity(ctx,intent, Bundle.EMPTY)
    }

    fun updateList(newList: List<OrderEntity>){
        orderList.clear()
        orderList.addAll(newList)
        notifyDataSetChanged()
    }

}