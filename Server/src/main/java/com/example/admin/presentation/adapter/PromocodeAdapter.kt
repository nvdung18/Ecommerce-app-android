package com.example.admin.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.data.room.product.ProductEntity
import com.example.admin.data.room.promocode.PromocodeEntity
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class PromocodeAdapter(private val ctx:Context):RecyclerView.Adapter<PromocodeAdapter.PromocodeViewHoder>() {
    private var promocodeList:ArrayList<PromocodeEntity> = arrayListOf()
    private var def=DecimalFormat()
    private var listener: PromocodeItemClickAdapter? = null

    fun setListener(listener: PromocodeItemClickAdapter) {
        this.listener = listener
    }

    inner class PromocodeViewHoder(itemView:View):RecyclerView.ViewHolder(itemView){
        val txtIdPromocode=itemView.findViewById<TextView>(R.id.txtIdPromocode)
        val txtDiscountPromocode=itemView.findViewById<TextView>(R.id.txtDiscountPromocode)
        val txtDescriptionPromocode=itemView.findViewById<TextView>(R.id.txtDescriptionPromocode)
        val btnEditPromocode=itemView.findViewById<Button>(R.id.btnEditPromocode)
        val btnDeletePromocode=itemView.findViewById<Button>(R.id.btnDeletePromocode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromocodeViewHoder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.single_promocode,parent,false)
        def = DecimalFormat("#,###.###")//use to format number like this: 100.000
        def.decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }
        return PromocodeViewHoder(view)
    }

    override fun getItemCount(): Int {
        return promocodeList.size
    }

    override fun onBindViewHolder(holder: PromocodeViewHoder, position: Int) {
        val promocodeItem:PromocodeEntity=promocodeList[promocodeList.size-1-position]
        holder.txtIdPromocode.text="Id Promocode: "+promocodeItem.idPromoCode
        holder.txtDiscountPromocode.text="Discount Percent: "+def.format(promocodeItem.discountPercent).toString()+"%"
        holder.txtDescriptionPromocode.text="Description: "+promocodeItem.description.toString()

        holder.btnDeletePromocode.setOnClickListener {
            deletePromocode(promocodeItem)
        }

        holder.btnEditPromocode.setOnClickListener {
            listener?.onItemUpdateClick(promocodeItem)
        }
    }

    private fun deletePromocode(promocode: PromocodeEntity) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to delete promocode?")
        builder.setPositiveButton("Yes") { dialog, which ->
            listener?.onItemDeleteClick(promocode)
        }
        builder.setNegativeButton("No") { dialog, which ->
            Log.e("a","false")
        }
        builder.show()
    }

    fun updateList(newList: List<PromocodeEntity>){
        promocodeList.clear()
        promocodeList.addAll(newList)
        notifyDataSetChanged()
    }
}
interface PromocodeItemClickAdapter{
    fun onItemDeleteClick(promocode: PromocodeEntity)
    fun onItemUpdateClick(promocode: PromocodeEntity) //edit

}