package com.example.ecommerce_app.adapter

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ecommerce_app.databinding.CartItemSingleBinding
import com.example.ecommerce_app.models.CartDetailsAndProduct
import com.example.ecommerce_app.models.CartDetailsAndProductAndBranch
import com.example.ecommerce_app.models.CartDetailsEntity
import com.example.ecommerce_app.models.ProductEntity
import java.text.NumberFormat
import java.util.Locale

class CartAdapter:Adapter<CartAdapter.HoldCart> {

    private lateinit var binding: CartItemSingleBinding
    lateinit var cartList: ArrayList<CartDetailsAndProductAndBranch>
    lateinit var listener: CartItemClickAdapter
    private lateinit var context: Context
    val uri_cartdetails: Uri = Uri.parse("content://com.example.admin/CartDetails")
    val localeVN: Locale = Locale("vi", "VN")
    val format = NumberFormat.getCurrencyInstance(localeVN)

    constructor(context: Context, cartList: ArrayList<CartDetailsAndProductAndBranch>, listener: CartItemClickAdapter) {
        this.context = context
        this.cartList = cartList
        this.listener = listener
    }

    inner class HoldCart(itemView: View): ViewHolder(itemView) {
        val cartImage = binding.cartImage
        val cartMore = binding.cartMore
        val cartName = binding.cartName
        val cartPrice = binding.cartPrice
        val quantityTvCart = binding.quantityTvCart
        val minusLay = binding.minusLayout
        val plusLay = binding.plusLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldCart {
        binding = CartItemSingleBinding.inflate(LayoutInflater.from(context), parent, false)
        return HoldCart(binding.root)
    }

    override fun onBindViewHolder(holder: HoldCart, position: Int) {
        val cartItem = cartList[position]

        holder.cartName.text = cartItem.nameProduct
        holder.cartPrice.text = format.format(cartItem.price).toString()
        holder.quantityTvCart.text = cartItem.quantity.toString()
        holder.cartMore.setOnClickListener {
            listener.onItemDeleteClick(cartItem)
        }

        Glide.with(context)
            .load(cartItem.image)
            .into(holder.cartImage)

        holder.minusLay.setOnClickListener {
            Log.d("CheckQuantity", "${cartItem.quantity.toInt()}")
            if (binding.quantityTvCart.text.toString().toInt() > 1) {
                holder.quantityTvCart.text = (cartItem.quantity.toInt()-1).toString()
                updateQuantityInCartByProduct(cartItem.idCart, cartItem.idProduct, cartItem.quantity.toInt()-1)
            }else if(binding.quantityTvCart.text.toString().toInt() == 1) {
                Toast.makeText(context, "Not minus just only min 1", Toast.LENGTH_SHORT).show()
            }
        }

        holder.plusLay.setOnClickListener {
            Log.d("CheckQuantity", "${cartItem.quantity.toInt()}")
            holder.quantityTvCart.text = (cartItem.quantity.toInt()+1).toString()
            updateQuantityInCartByProduct(cartItem.idCart, cartItem.idProduct, cartItem.quantity.toInt()+1)
        }

    }

    private fun updateQuantityInCartByProduct(idCart: String, idProduct: String, quantity: Int) {
        val values = ContentValues().apply {
            put("quantity", quantity)
        }
        val uri = context.contentResolver.update(uri_cartdetails, values, "idCart = ? and idProduct = ?", arrayOf(idCart, idProduct))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun getPrice(): Double {
        var totalPrice = 0.0
        for (cart in cartList) {
            totalPrice += cart.price * cart.quantity
        }
        return totalPrice
    }
}

interface CartItemClickAdapter{
    fun onItemDeleteClick(product: CartDetailsAndProductAndBranch)
}