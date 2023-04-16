package com.example.admin.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin.R
import com.example.admin.data.model.OrderDetailsAndProduct
import com.example.admin.data.room.product.ProductEntity
import com.example.admin.data.room.product.ProductViewModel

class ProductAdapter(private val ctx: Context,private val viewModel: ProductViewModel):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val productList:ArrayList<ProductEntity> = arrayListOf()

    private var listener: ProductItemClickAdapter? = null

    fun setListener(listener: ProductItemClickAdapter) {
        this.listener = listener
    }
    inner class ProductViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val txtIdProduct=itemView.findViewById<TextView>(R.id.txtIdProduct)
        val txtNameProduct=itemView.findViewById<TextView>(R.id.txtNameProduct)
        val imgSigleProduct=itemView.findViewById<ImageView>(R.id.imgSigleProduct)
        val btnDeleteProduct=itemView.findViewById<Button>(R.id.btnDeleteProduct)
        val btnDetailsProduct=itemView.findViewById<Button>(R.id.btnDetailsProduct)
        val btnEditProduct=itemView.findViewById<Button>(R.id.btnEditProduct)

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

        var listOrderDtAndProduct = viewModel.getProductAndOrderDtById(productItem.idProduct)
        var checkProductNullOrder:Boolean=true// If true, there are no orders and receipt for this product => we can delete this product
        if(!listOrderDtAndProduct.isEmpty()) checkProductNullOrder=false
//        Log.e("a",listOrderDtAndProduct.toString())
//        Log.e("Id: ",productItem.idProduct.toString()+" "+checkProductNullOrder.toString())


        if(checkProductNullOrder==false){
            holder.btnDeleteProduct.isEnabled=false
        }else{
            holder.btnDeleteProduct.setOnClickListener {
                deleteBranch(productItem)
            }
        }

        holder.btnDetailsProduct.setOnClickListener {
            listener?.onDetailsItemClick(productItem)
        }

        holder.btnEditProduct.setOnClickListener {
            listener?.onItemUpdateClick(productItem)
        }



    }

    private fun deleteBranch(productItem:ProductEntity) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to delete Product?")
        builder.setPositiveButton("Yes") { dialog, which ->
            listener?.onItemDeleteClick(productItem)
        }
        builder.setNegativeButton("No") { dialog, which ->
            Log.e("a","false")
        }
        builder.show()
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
interface ProductItemClickAdapter{
    fun onItemDeleteClick(product: ProductEntity)
    fun onItemUpdateClick(product: ProductEntity) //edit

    fun onDetailsItemClick(product: ProductEntity)

}