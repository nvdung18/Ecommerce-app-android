package com.example.ecommerce_app.fragment

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapter.OrderUserAdapter
import com.example.ecommerce_app.databinding.FragmentOrderUserBinding
import com.example.ecommerce_app.models.BrandAndModel
import com.example.ecommerce_app.models.OrderAndOrderdetails
import com.example.ecommerce_app.models.StatusOrder
import com.google.common.reflect.TypeToken
import com.google.firestore.v1.StructuredQuery.Order
import com.google.gson.Gson

class OrderUserFragment : Fragment {

    private lateinit var binding: FragmentOrderUserBinding
    private lateinit var contentResolver:ContentResolver
    private var uri_order:Uri=Uri.parse("content://com.example.admin/OrderTable")
    private lateinit var idAccount:String
    val uri_account: Uri = Uri.parse("content://com.example.admin/account")
    private lateinit var adapter:OrderUserAdapter
    private var listOrder=mutableListOf<OrderAndOrderdetails>()
    private var listProduct=mutableListOf<BrandAndModel>()
    private var targetOrderList=mutableListOf<ArrayList<OrderAndOrderdetails>>()//to get order depend on status
    private val productMap= mutableMapOf<String, BrandAndModel>()

    companion object {
        private const val TAG = "ORDER_USER_TAG"

        //receive data from activity to load product
        public fun newInstance(
            idstateProduct: String,
            stateProduct: String,
            idAc: String
        ): OrderUserFragment {
            val fragment = OrderUserFragment()
            val args = Bundle()
            args.putString("idstateProduct", idstateProduct)
            args.putString("stateProduct", stateProduct)
            args.putString("idAc", idAc)
            fragment.arguments = args
            return fragment
        }
    }

    private var idstateProduct = ""
    private var stateProduct = ""
    private var idAc = ""

    //arraylist to hold product


    //constructor
    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idAccount= getCurrentIdAccount().toString()
        val args = arguments
        if(args != null) {
            idstateProduct = args.getString("idstateProduct")!!
            stateProduct = args.getString("stateProduct")!!
            idAc = args.getString("idAc")!!
        }

    }

    private fun getAllOrderUser() {
        listOrder.clear()
        var cursor=contentResolver.query(uri_order,
            null,
            "idAccount = ?",
            arrayOf(idAccount),
            null)?:null


        if(cursor!=null){
            cursor?.let {
                while (it.moveToNext()) {
                    //order
                    val idOrder = it.getString(cursor.getColumnIndexOrThrow("idOrder"))
                    val status=it.getString(cursor.getColumnIndexOrThrow("status"))
                    val orderNotes=it.getString(cursor.getColumnIndexOrThrow("orderNotes"))
                    val deliveryCharges=it.getString(cursor.getColumnIndexOrThrow("deliveryCharges"))
                    val productMoney=it.getString(cursor.getColumnIndexOrThrow("productMoney"))
                    val idPayment=it.getString(cursor.getColumnIndexOrThrow("idPayment"))
                    val idPromoCode=it.getString(cursor.getColumnIndexOrThrow("idPromoCode"))
                    val idCheckout=it.getString(cursor.getColumnIndexOrThrow("idCheckout"))
                    val discountPercent=it.getString(cursor.getColumnIndexOrThrow("discountPercent"))
                    val description=it.getString(cursor.getColumnIndexOrThrow("description"))
                    //product
                    val nameProduct=it.getString(cursor.getColumnIndexOrThrow("nameProduct"))
                    val idProduct=it.getString(cursor.getColumnIndexOrThrow("idProduct"))
                    val quantity=it.getString(cursor.getColumnIndexOrThrow("quantity"))
                    val image=it.getString(cursor.getColumnIndexOrThrow("image"))
                    val price=it.getString(cursor.getColumnIndexOrThrow("price"))
                    val idBranch=it.getString(cursor.getColumnIndexOrThrow("idBranch"))
                    var nameBranch=it.getString(cursor.getColumnIndexOrThrow("nameBranch"))

                    //create object
                    var order=OrderAndOrderdetails(idOrder,status,orderNotes,
                        deliveryCharges.toFloat(),productMoney.toDouble(),
                        idPayment,idPromoCode,idCheckout,idProduct,quantity.toInt(),description,discountPercent.toFloat())
                    var product=BrandAndModel(idProduct,nameProduct,image,price.toDouble(),"","",
                        0F,0,idBranch,nameBranch)

//                    Log.e("a",order.toString())

                    //add object into list
                    listOrder.add(order)
                    if(!productMap.contains(idProduct)){
                        productMap[idProduct]=product
                    }
                }
                it.close()
            }
        }else{
            Log.e("cursor","null")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderUserBinding.inflate(LayoutInflater.from(context), container, false)
        contentResolver=requireContext().contentResolver
        getAllOrderUser()
        if(stateProduct == "Wait for confirmation") {
            loadProductConfirm()
        } else if (stateProduct == "Order confirmed") {
            loadProductToGetGood()
        } else if (stateProduct == "Delivering") {
            loadProductToGotGood()
        } else if (stateProduct == "Order delivered") {
            loadProductToMadePay()
        }
        return binding.root
    }

    private fun loadProductConfirm() {
        binding.cmTv.text = "${stateProduct}"
        targetOrderList.clear()
        targetOrderList= getOrderByStatus(stateProduct) as MutableList<ArrayList<OrderAndOrderdetails>>
        loadAdapter(targetOrderList)
    }


    private fun loadProductToGetGood() {
        binding.cmTv.text = "${stateProduct}"
        targetOrderList.clear()
        targetOrderList= getOrderByStatus(stateProduct) as MutableList<ArrayList<OrderAndOrderdetails>>
        loadAdapter(targetOrderList)
    }

    private fun loadProductToGotGood() {
        binding.cmTv.text = "${stateProduct}"
        targetOrderList.clear()
        targetOrderList= getOrderByStatus(stateProduct) as MutableList<ArrayList<OrderAndOrderdetails>>
        loadAdapter(targetOrderList)
    }

    private fun loadProductToMadePay() {
        binding.cmTv.text = "${stateProduct}"
        targetOrderList.clear()
        targetOrderList= getOrderByStatus(stateProduct) as MutableList<ArrayList<OrderAndOrderdetails>>
        loadAdapter(targetOrderList)
    }

    private fun loadAdapter(tgOrderList: List<List<OrderAndOrderdetails>>){
        adapter= OrderUserAdapter(activity as Context,
            tgOrderList as ArrayList<List<OrderAndOrderdetails>>,productMap,stateProduct
        )
        binding.rvListOrderUser.adapter=adapter
        binding.rvListOrderUser.layoutManager= LinearLayoutManager(
            activity as Context,
            LinearLayoutManager.VERTICAL,
            false
        )
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

    private fun getOrderByStatus(status:String):List<List<OrderAndOrderdetails>> {
        var targetOrderListChild= mutableListOf<ArrayList<OrderAndOrderdetails>>()
        var orderListItemTarget= mutableListOf<OrderAndOrderdetails>()
        targetOrderListChild.clear()
        var listIdOrder= mutableListOf<String>()
        var gson=Gson()
        var jsonStatus=""
        //get all status of all order
        for (order in listOrder){
//            Log.e("index",order.idOrder)
            if(!listIdOrder.contains(order.idOrder)){
                jsonStatus=order.status
                //convert json to object
                val type = object : TypeToken<ArrayList<StatusOrder>>() {}.type
                val statusOrderList: ArrayList<StatusOrder> = gson.fromJson(jsonStatus, type)

                //get last element of statusOrderList and check if it equal value status of user want to access
                val statusItem=statusOrderList[statusOrderList.size-1]
//                Log.e("Order",order.toString())
                if(statusItem.statusOrder==status){
//                    Log.e("id",order.idOrder)
                    if(orderListItemTarget.size>0){
                        targetOrderListChild.add(ArrayList(orderListItemTarget))
//                        Log.e("target",targetOrderListChild.toString())
                        orderListItemTarget.clear()
//                        Log.e("targetClear",targetOrderListChild.toString())
                    }
                    listIdOrder.add(order.idOrder)
                    orderListItemTarget.add(order)
//                    Log.e("order1",order.toString())
//                    Log.e("listId",listIdOrder.toString())
                }
            }else{
//                Log.e("order2",order.toString())
                orderListItemTarget.add(order)
            }
        }

        if(orderListItemTarget.size>0){
            targetOrderListChild.add(ArrayList(orderListItemTarget))
//            Log.e("target2",targetOrderListChild.toString())
            orderListItemTarget.clear()
        }
//        var i=0
//        Log.e("size",targetOrderListChild.size.toString())
//        for(orderi in targetOrderListChild){
//            Log.e("a",i++.toString())
//            for(o in orderi){
//                Log.e("o",o.toString())
//            }
//        }
        return targetOrderListChild
    }
}