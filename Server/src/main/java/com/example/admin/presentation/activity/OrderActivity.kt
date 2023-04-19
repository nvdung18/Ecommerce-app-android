package com.example.admin.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.data.model.StatusOrder
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.checkout.CheckoutEntity
import com.example.admin.data.room.detailsOrder.OrderDetailsEntity
import com.example.admin.data.room.order.OrderEntity
import com.example.admin.data.room.order.OrderViewModel
import com.example.admin.data.room.payment.PaymentEntity
import com.example.admin.data.room.promocode.PromocodeEntity
import com.example.admin.databinding.ActivityOrderBinding
import com.example.admin.presentation.adapter.OrderAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOrderBinding
    private lateinit var adapter: OrderAdapter
    private lateinit var orderviewModel:OrderViewModel
    private lateinit var funActivity:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        binding= ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i=intent
        funActivity=i.getStringExtra("fun").toString()
        if (funActivity=="Receipt"&&funActivity!=null){
            InterfaceReceipt(funActivity)
        }

        initComponents()

        orderviewModel= ViewModelProviders.of(this).get(OrderViewModel::class.java)

        orderviewModel.allOrder.observe(this,{List->
            List?.let {
                if (funActivity=="Receipt"&&funActivity!=null){
                    var listReceipt=orderviewModel.getAllReceipt()
                    var listOrderFilter=ArrayList<OrderEntity>()
                    for(itemOrder in it){
                        for(itemReceipt in listReceipt){
                            if(itemReceipt.idOrder==itemOrder.idOrder){
                                listOrderFilter.add(itemOrder)
                            }
                        }
                    }
                    adapter.updateList(listOrderFilter)
                }else{
                    adapter.updateList(it)
                }
            }

        })
    }

    private fun initComponents() {
        adapter= OrderAdapter(this,funActivity)
        binding.rvAllOrder.adapter=adapter
        binding.rvAllOrder.layoutManager=LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.imgBtnOrderBack.setOnClickListener {
            back()
        }


//        val listTest=ArrayList<BranchEntity>()
//        listTest.add(BranchEntity("asdsd","ashdj"))
//        listTest.add(BranchEntity("asd","gfd"))
//        listTest.add(BranchEntity("afvd","gvcv"))
//        listTest.add(BranchEntity("aas","xcz"))
//        val gson = Gson()
//        val json = gson.toJson(listTest)
//        Log.e("Json",json.toString())

//        if(funActivity!="Receipt"){
//            addSampleOrder()
//        }
    }

    private fun InterfaceReceipt(funActivity:String) {
        binding.txtTitleInterace.text=funActivity
        binding.txtTitleRv.text="All ${funActivity}"
        binding.actxtFind.hint="Enter your receipt you want to find"
    }



    private fun back() {
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun addSampleOrder() {

        var formatterDate = SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        var formatterTime = SimpleDateFormat( "HH:mm:ss", Locale.getDefault());
        var now = Date();
        var date=formatterDate.format(now)
        Log.e("Date",date.toString())

        val listStatus= ArrayList<StatusOrder>()
        listStatus.add(StatusOrder(Date(date),"Wait for confirmation"))

        Log.e("List",listStatus.toString())

        val gson = Gson()
        val json = gson.toJson(listStatus)
//        Log.e("Json",json.toString())
//
//        val type = object : TypeToken<ArrayList<StatusOrder>>() {}.type
//        val statusList: ArrayList<StatusOrder> = gson.fromJson(json, type)
//        Log.e("Date json",formatterDate.format(statusList[0].date).toString())
//        Log.e("Time json",formatterTime.format(statusList[0].date).toString())


        var instance=AppDatabase.getInstance(this)

        instance.paymentDao().insertPayment(PaymentEntity("pay01","ship cod",0.0))
        instance.promocodeDao().insertPromocode(PromocodeEntity("promo_01","No promotion",0F))
        instance.checkoutDao().insertCheckout(CheckoutEntity("ck_01","XYZ",973884531,"xyzk123@gmail.com"
            ,"723 Lê Văn Hiến, Ngũ Hành Sơn, TP.Đà Nẵng","idAccount_2"))

        instance.orderDao().insertOrder(OrderEntity("ord_01",json,"asdkh"
            ,0.0,395000.0,"idAccount_2","pay01","promo_01","ck_01"))

        instance.orderDetailsDao().insertOrderDetails(OrderDetailsEntity("ord_01","SP01",272000.0,1))
        instance.orderDetailsDao().insertOrderDetails(OrderDetailsEntity("ord_01","SP02",123000.0,1))

        instance.checkoutDao().insertCheckout(CheckoutEntity("ck_02","XYZ",973884531,"xyzk123@gmail.com"
            ,"723 Lê Văn Hiến, Ngũ Hành Sơn, TP.Đà Nẵng","idAccount_2"))

        instance.orderDao().insertOrder(OrderEntity("ord_02",json,"asdkh"
            ,0.0,710000.0,"idAccount_2","pay01","promo_01","ck_02"))

        instance.orderDetailsDao().insertOrderDetails(OrderDetailsEntity("ord_02","SP05",310000.0,1))
        instance.orderDetailsDao().insertOrderDetails(OrderDetailsEntity("ord_02","SP08",400000.0,1))

        instance.checkoutDao().insertCheckout(CheckoutEntity("ck_03","XYZ",973884531,"xyzk123@gmail.com"
            ,"723 Lê Văn Hiến, Ngũ Hành Sơn, TP.Đà Nẵng","idAccount_1"))

        instance.orderDao().insertOrder(OrderEntity("ord_03",json,"asdkh"
            ,0.0,710000.0,"idAccount_1","pay01","promo_01","ck_03"))

        instance.orderDetailsDao().insertOrderDetails(OrderDetailsEntity("ord_03","SP05",310000.0,1))
        instance.orderDetailsDao().insertOrderDetails(OrderDetailsEntity("ord_03","SP08",400000.0,1))

        instance.checkoutDao().insertCheckout(CheckoutEntity("ck_04","XYZ",973884531,"xyzk123@gmail.com"
            ,"723 Lê Văn Hiến, Ngũ Hành Sơn, TP.Đà Nẵng","idAccount_1"))

        instance.orderDao().insertOrder(OrderEntity("ord_04",json,"asdkh"
            ,0.0,710000.0,"idAccount_1","pay01","promo_01","ck_04"))

        instance.orderDetailsDao().insertOrderDetails(OrderDetailsEntity("ord_04","SP05",310000.0,1))
        instance.orderDetailsDao().insertOrderDetails(OrderDetailsEntity("ord_04","SP08",400000.0,1))
    }
}