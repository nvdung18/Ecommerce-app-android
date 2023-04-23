package com.example.ecommerce_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ecommerce_app.databinding.RowProductCheckoutBinding
import com.example.ecommerce_app.models.CartDetailsAndProduct
import com.example.ecommerce_app.models.CartDetailsAndProductAndBranch
import java.text.DecimalFormat

class ProductCheckOutAdapter:Adapter<ProductCheckOutAdapter.HolderProductCheckout> {

    private lateinit var context: Context
    private lateinit var listProductCheckout: ArrayList<CartDetailsAndProductAndBranch>
    private lateinit var binding: RowProductCheckoutBinding
    constructor(context: Context, listProductCheckout: ArrayList<CartDetailsAndProductAndBranch>) {
        this.context = context
        this.listProductCheckout = listProductCheckout
    }

    inner class HolderProductCheckout(itemView: View): ViewHolder(itemView) {
        val nameProduct = binding.nameproTv
        val categoryProduct = binding.categoryproductTv
        val priceProduct = binding.priceCheckout
        val quantityProduct = binding.quantityproductTv
        val image = binding.imgProductCheckout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderProductCheckout {
       binding = RowProductCheckoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderProductCheckout(binding.root)
    }

    override fun onBindViewHolder(holder: HolderProductCheckout, position: Int) {
        val model = listProductCheckout[position]
        holder.nameProduct.text = model.nameProduct
        holder.categoryProduct.text = model.idBranch
        val vndFormat = DecimalFormat("#,### VND")
        val priceNew = model.price.toString().replace(",", ".")
        val priceNewNumber = priceNew.toDouble() // convert string to double
        val formattedAmount = vndFormat.format(priceNewNumber) // format double as VND
        holder.priceProduct.text = formattedAmount
        holder.quantityProduct.text = model.quantity.toString()
        Glide.with(context)
            .load(model.image)
            .into(holder.image)
    }

    fun getPrice(): Double {
        var totalPrice = 0.0
        for (cart in listProductCheckout) {
            totalPrice += cart.price * cart.quantity
        }
        return totalPrice
    }

    override fun getItemCount(): Int {
         return listProductCheckout.size
    }
}