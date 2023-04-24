package com.example.ecommerce_app.fragment

import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activity.AllProductActivity
import com.example.ecommerce_app.activity.CheckoutActivity
import com.example.ecommerce_app.adapter.CartAdapter
import com.example.ecommerce_app.adapter.CartItemClickAdapter
import com.example.ecommerce_app.adapter.ProductAdapter
import com.example.ecommerce_app.databinding.FragmentBagBinding
import com.example.ecommerce_app.models.BrandAndModel
import com.example.ecommerce_app.models.CartDetailsAndProduct
import com.example.ecommerce_app.models.CartDetailsAndProductAndBranch


class BagFragment : Fragment(), CartItemClickAdapter {

    private lateinit var binding: FragmentBagBinding
    lateinit var cartAdapter: CartAdapter
    lateinit var listBranchModel : ArrayList<BrandAndModel>
    lateinit var cartList: ArrayList<CartDetailsAndProductAndBranch>
    val uri_CartDetails: Uri = Uri.parse("content://com.example.admin/CartDetails")
    val uri_cart: Uri = Uri.parse("content://com.example.admin/Cart")
    val uri_account: Uri = Uri.parse("content://com.example.admin/account")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBagBinding.inflate(LayoutInflater.from(context), container, false)
        cartList = ArrayList()
        listBranchModel = ArrayList()
        getDataCardDetailsFromDatabase()
        cartAdapter = CartAdapter(activity as Context, cartList, this)


        val observe: ContentObserver = object : ContentObserver(Handler()) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                cartList.clear()
                getDataCardDetailsFromDatabase()
                cartAdapter.notifyDataSetChanged()
                binding.totalPriceBagFrag.text = cartAdapter.getPrice().toString()
            }
        }

        context?.contentResolver?.registerContentObserver(uri_CartDetails, true, observe)

        binding.cartRecView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = cartAdapter
        }
        binding.totalPriceBagFrag.text = cartAdapter.getPrice().toString()

        binding.checkOutBagPage.setOnClickListener {
            val intent = Intent(activity as Context, CheckoutActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("ARRAYLIST", cartAdapter.cartList)
            intent.putExtra("coverProduct", bundle)
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        }
        return binding.root
    }

    private fun getDataCardDetailsFromDatabase() {
        if(getCurrentIdCart() != null) {
            val idCurrentCart = getCurrentIdCart()
            val cursor = context?.contentResolver?.query(uri_CartDetails, null, "idCart = ?", arrayOf(idCurrentCart), null)
            if(cursor != null && cursor.moveToFirst()) {
                do {
                    val quantity = cursor.getString(cursor.getColumnIndexOrThrow("quantity")).toInt()
                    val idCart = cursor.getString(cursor.getColumnIndexOrThrow("idCart"))
                    val idProduct = cursor.getString(cursor.getColumnIndexOrThrow("idProduct"))
                    val nameProduct = cursor.getString(cursor.getColumnIndexOrThrow("nameProduct"))
                    val image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
                    val price = cursor.getString(cursor.getColumnIndexOrThrow("price")).toDouble()
                    val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                    val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                    val sale = cursor.getString(cursor.getColumnIndexOrThrow("sale")).toFloat()
                    val soldQuantity = cursor.getString(cursor.getColumnIndexOrThrow("soldQuantity")).toInt()
                    val idBranch = cursor.getString(cursor.getColumnIndexOrThrow("idBranch"))
                    val nameBranch = cursor.getString(cursor.getColumnIndexOrThrow("nameBranch"))
                    cartList.add(CartDetailsAndProductAndBranch(quantity, idCart, idProduct, nameProduct, image, price, description, type, sale, soldQuantity, idBranch, nameBranch))
                } while (cursor.moveToNext())
            }
        }
    }

    override fun onItemDeleteClick(product: CartDetailsAndProductAndBranch) {
        context?.contentResolver?.delete(uri_CartDetails, "idCart = ? and idProduct = ? and quantity = ?", arrayOf(product.idCart, product.idProduct, product.quantity.toString()))
    }

    private fun getCurrentIdCart(): String? {
        if(getCurrentIdAccount() != null) {
            val idAccount = getCurrentIdAccount().toString()
            val cursor = context?.contentResolver?.query(uri_cart, null, "idAccount = ?", arrayOf(idAccount), null)
            if(cursor != null) {
                if(cursor.moveToFirst()) {
                    val idCart = cursor.getString(cursor.getColumnIndexOrThrow("idCart"))
                    return idCart
                }
                cursor.close()
            }
        }
        return null
    }

    public fun getCurrentIdAccount(): String? {
        val sharedPreferences = context?.getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        val cursor = context?.contentResolver?.query(uri_account, null, "token = ?", arrayOf(token), null)
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                val idAccount = cursor.getString(cursor.getColumnIndexOrThrow("idAccount"))
                return idAccount
            }
            if(cursor != null) {
                cursor.close()
            }

        }
        return null
    }
}