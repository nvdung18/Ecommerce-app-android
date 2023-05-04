package com.example.ecommerce_app.activity

import android.content.Intent
import android.database.Cursor
import android.net.Uri
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
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
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
    private lateinit var def:DecimalFormat
    private val uri_checkout: Uri = Uri.parse("content://com.example.admin/Checkout")
    private lateinit var cursor:Cursor
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

        //get cursor checkout information
        cursor= (contentResolver.query(uri_checkout,
            null,
            "idCheckout = ?",
            arrayOf(orderListItem[0].idCheckout),
            null)?:null)!!


        //set all information for details order
        setInformation()

        //function
        binding.backImg.setOnClickListener {
            back()
        }
    }

    private fun setInformation() {
        def = DecimalFormat("#,###.###")//use to format number like this: 100.000
        def.decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }
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

        //set address
        if(cursor!=null){
            cursor.let {
                while (it.moveToNext()){
                    binding.txtReceiptName.text = it.getString(cursor.getColumnIndexOrThrow("recipientName"))
                    binding.txtReceiptPhoneNumber.text = it.getString(cursor.getColumnIndexOrThrow("recipientPhoneNumber"))
                    binding.txtReceiptEmail.text = it.getString(cursor.getColumnIndexOrThrow("recipientEmail"))
                    binding.txtReceiptAddress.text = it.getString(cursor.getColumnIndexOrThrow("recipientAddress"))
                    Log.e("a",it.toString())
                }
            }
        }

        //set price and time delivery
        binding.txtTotalOrder.text=def.format(priceOfficial).toString()
        if(anOrder.idPayment=="pay01"){
            binding.txtNotificationPayment.visibility= View.VISIBLE
            binding.txtNotificationPayment.text="Please pay ${def.format(priceOfficial)} when receiving goods "
        }
        binding.txtIdOrder.text=anOrder.idOrder

        if (statusList.size>=1){
            binding.txtTimeWaitForConfirmation.visibility= View.VISIBLE
            binding.txtTimeWaitForConfirmation.text=formatterDate.format(statusList[0].date)
        }
        if(statusList.size>=2){
            binding.txtTitleTimeOConfirmationT.visibility=View.VISIBLE
            binding.txtTimeOrderConfirmed.visibility= View.VISIBLE
            binding.txtTimeOrderConfirmed.text=formatterDate.format(statusList[1].date)
        }
        if(statusList.size>=3){
            binding.txtTitleTimeDT.visibility= View.VISIBLE
            binding.txtTimeDelivering.visibility= View.VISIBLE
            binding.txtTimeDelivering.text=formatterDate.format(statusList[2].date)
        }
        if(statusList.size>=4){
            binding.txtTitleTimeOCompletionT.visibility= View.VISIBLE
            binding.txtTimeOrderDelivered.visibility= View.VISIBLE
            Log.e("asd",statusList[3].date.toString())
            binding.txtTimeOrderDelivered.text=formatterDate.format(statusList[3].date)
        }
    }

    private fun back() {
        var intent=Intent(this,OrderActivity::class.java)
        startActivity(intent)
    }
}