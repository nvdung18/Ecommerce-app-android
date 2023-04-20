package com.example.ecommerce_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapter.RowProductOrderUserAdapter
import com.example.ecommerce_app.databinding.ActivityOrderDetailBinding
import com.example.ecommerce_app.fragment.OrderUserFragment
import com.example.ecommerce_app.models.BrandAndModel
import com.example.ecommerce_app.models.OrderAndOrderdetails
import com.example.ecommerce_app.models.StatusOrder
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var orderListItem:List<OrderAndOrderdetails>
    private lateinit var productMap:Map<String,BrandAndModel>
    private lateinit var adapter:RowProductOrderUserAdapter
    private lateinit var stateOrder:String
    private var statusDetailsMap=mutableMapOf<String, String>()
    private var gson=Gson()
    private var formatterDate = SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //create a map use for write details order
        createContentStatus()
        //init
        initComponents()
    }

    private fun createContentStatus() {
        statusDetailsMap["Wait for confirmation"]="The order is waiting confirmation"
        statusDetailsMap["Order confirmed"]="The order has been confirmed"
        statusDetailsMap["Delivering"]="The orders are being delivered"
        statusDetailsMap["Order delivered"]="The order has been delivered"
    }

    private fun initComponents() {
        //get information from OrderUserAdapter
        var i=intent
        var jsonOrderListItem=i.getStringExtra("jsonOrderListItem")
        var jsonProductMap=i.getStringExtra("jsonProductMap")
        stateOrder=i.getStringExtra("stateOrder").toString()

        //convert json to list,map,...
        gson=Gson()

        val typeOrderListItem = object : TypeToken<ArrayList<OrderAndOrderdetails>>() {}.type
        orderListItem = gson.fromJson(jsonOrderListItem, typeOrderListItem)

        val typeProductMap = object : TypeToken<Map<String,BrandAndModel>>() {}.type
        productMap = gson.fromJson(jsonProductMap, typeProductMap)

        statusDetailsMap.get(stateOrder).toString()
        //create apdater
        adapter= RowProductOrderUserAdapter(this,
            ArrayList(orderListItem),productMap, stateOrder
        )
        binding.rvListOrderUser.adapter=adapter
        binding.rvListOrderUser.layoutManager= LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        //set information for details order
        setInformation()

        //function
        binding.backImg.setOnClickListener {
            back()
        }
    }

    private fun setInformation() {
        //get status
        var anOrder=orderListItem[0]
        var jsonStatus=orderListItem[0].status
        val typeStatus = object : TypeToken<ArrayList<StatusOrder>>() {}.type
        val statusList: ArrayList<StatusOrder> = gson.fromJson(jsonStatus, typeStatus)

        val currentStatus=statusList[statusList.size-1]
        var priceOfficial:Double=(anOrder.productMoney-((anOrder.productMoney*anOrder.discountPercent)/100))+anOrder.deliveryCharges
        binding.txtTitleCurrentStatusOrder.text=currentStatus.statusOrder
        binding.txtCurrentStatus.text=currentStatus.statusOrder
        binding.txtCurrentTimeStatus.text=formatterDate.format(currentStatus.date)
//        binding.txtReceiptName.text
//        binding.txtReceiptPhoneNumber.text
//        binding.txtReceiptEmail.text
//        binding.txtReceiptAddress.text
        binding.txtTotalOrder.text=priceOfficial.toString()
        if(anOrder.idPayment=="pay01"){
            binding.txtNotificationPayment.visibility= View.VISIBLE
            binding.txtNotificationPayment.text="Please pay ${priceOfficial} when receiving goods "
        }
        binding.txtIdOrder.text=anOrder.idOrder

        if (statusList.size>=1){
            binding.txtTimeWaitForConfirmation.visibility= View.VISIBLE
            binding.txtTimeWaitForConfirmation.text=formatterDate.format(statusList[0].date)
        }
        if(statusList.size>=2){
            binding.txtTimeOrderConfirmed.visibility= View.VISIBLE
            binding.txtTimeOrderConfirmed.text=formatterDate.format(statusList[1].date)
        }
        if(statusList.size>=3){
            binding.txtTimeDelivering.visibility= View.VISIBLE
            binding.txtTimeDelivering.text=formatterDate.format(statusList[2].date)
        }
        if(statusList.size>=4){
            binding.txtTimeOrderConfirmed.visibility= View.VISIBLE
            binding.txtTimeOrderConfirmed.text=formatterDate.format(statusList[3].date)
        }
    }

    private fun back() {
        var intent=Intent(this,OrderActivity::class.java)
        startActivity(intent)
    }
}