package com.example.admin.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.data.model.StatusOrder
import com.example.admin.data.room.order.OrderEntity
import com.example.admin.data.room.order.OrderViewModel
import com.example.admin.data.room.receipt.ReceiptEntity
import com.example.admin.databinding.ActivityDetailsOrderBinding
import com.example.admin.presentation.adapter.DetailsOrderAdapter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailsOrderActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsOrderBinding
    private lateinit var idOrder:String
    private lateinit var viewModel: OrderViewModel
    private lateinit var orderItem:OrderEntity
    private lateinit var adapter: DetailsOrderAdapter
    private lateinit var def:DecimalFormat
    private lateinit var nowStatusOrder:String
    private lateinit var funActivity:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_order)

        binding=ActivityDetailsOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProviders.of(this).get(OrderViewModel::class.java)

        initComponents()
    }

    private fun initComponents() {

//        get idOrder from another activity then get orderEntity (Item) and get funActivity to determine interface
        var i=intent
        idOrder= i.getStringExtra("idOrder").toString() //get idOrder
        funActivity=i.getStringExtra("fun").toString() //get funActivity

        orderItem=viewModel.getOrderById(idOrder)
        nowStatusOrder=orderItem.status //get current status of order

//        setup for spinner status order
        setupSpinnerChangeStatus(orderItem)

//        set information for Total payment
        def = DecimalFormat("#,###.###")//use to format number like this: 100.000
        def.decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }

        var promodeCodeEntity=viewModel.getPromocodeById(orderItem.idPromoCode)
        var paymentEntity=viewModel.getPaymentById(orderItem.idPayment)

        binding.txtTotalAllProduct.text=def.format(orderItem.productMoney).toString()
        binding.txtShippingFee.text=orderItem.deliveryCharges.toString()
        binding.txtPromotion.text=promodeCodeEntity.discountPercent.toString()
        binding.txtFromPayment.text=paymentEntity.namePayment
        binding.txtTotalPayment.text=def.format((orderItem.deliveryCharges+orderItem.productMoney+promodeCodeEntity.discountPercent)).toString()

//        set information of user
        InformationOfUser(orderItem)

//        set information for Details order (information of product)
        InformationDetailsOrder(idOrder)

//        back
        binding.imgBtnDetailsOrderBack.setOnClickListener {
            back()
        }


    }

    private fun back() {
        val intent=Intent(this,OrderActivity::class.java)
        intent.putExtra("fun",funActivity)
        startActivity(intent)
    }

    private fun InformationDetailsOrder(idOrder:String) {

        var listItemProduct=viewModel.getOrderDetailsById(idOrder)

        adapter= DetailsOrderAdapter(this,listItemProduct)
        binding.rvAllDetailsOrder.adapter=adapter
        binding.rvAllDetailsOrder.layoutManager= LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

    }

    private fun InformationOfUser(orderItem:OrderEntity) {

        var idCheckout=orderItem.idCheckout //get idCheckout to get information of receiver
        var checkout=viewModel.getCheckoutById(idCheckout)

        binding.txtNameCheckoutUser.text=checkout.recipientName
        binding.txtPhoneCheckoutUser.text=checkout.recipientPhoneNumber.toString()
        binding.txtAdressCheckoutUser.text=checkout.recipientAddress
        binding.txtEmailCheckoutuser.text=checkout.recipientEmail
    }


    private fun setupSpinnerChangeStatus(orderItem:OrderEntity) {
//        get now status and change it from json to list to set status of order (wait for confirmation, delivering,...)
        val nowStatus=orderItem.status
        val gson = Gson()
        val type = object : TypeToken<ArrayList<StatusOrder>>() {}.type
        val statusList: ArrayList<StatusOrder> = gson.fromJson(nowStatus, type)
        val size=statusList.size

        val listStatusSrc=resources.getStringArray(R.array.statusOrder)
        var listStatusChoose=ArrayList<String>()

        for (i in 0 until  listStatusSrc.size-1){
            if(listStatusSrc[i]==statusList[size-1].statusOrder){
                listStatusChoose.add(listStatusSrc[i])
                listStatusChoose.add(listStatusSrc[i+1])
            }
        }
        if(statusList[size-1].statusOrder=="Order delivered"){
            var arrList=ArrayList<String>()
            arrList.add("Order delivered")
            listStatusChoose=arrList
            binding.btnChangeStatus.isEnabled=false
        }

        if(funActivity=="Receipt"){
            binding.btnChangeStatus.visibility=View.GONE
            binding.spStatusOrder.isEnabled=false
        }

            val adt=ArrayAdapter(this,
                android.R.layout.simple_spinner_item,listStatusChoose)

            binding.spStatusOrder.adapter=adt
            binding.spStatusOrder.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (listStatusChoose[position]==statusList[size-1].statusOrder){
                        binding.btnChangeStatus.isEnabled=false
                    }else{
                        binding.btnChangeStatus.isEnabled=true
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.btnChangeStatus.isEnabled=false
                }
            }





//        Confirm change status
        binding.btnChangeStatus.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmation")
            builder.setMessage("Are you sure you want to change status?")
            builder.setPositiveButton("Yes") { dialog, which ->
                ChangeStatusOrder(listStatusChoose[1])
            }
            builder.setNegativeButton("No") { dialog, which ->
                Log.e("a","false")
            }
            builder.show()
        }
    }

//    Change status (Update)
    private fun ChangeStatusOrder(statusChoose:String) {
            var formatterDate = SimpleDateFormat( "MM/dd/yyyy HH:mm:ss", Locale.getDefault()); //use to format date to get now status and update status
            var formatterTime = SimpleDateFormat( "HH:mm:ss", Locale.getDefault());
            var now = Date();
            var date=formatterDate.format(now)

            val gson = Gson() //use to convert json to arrayList
            val type = object : TypeToken<ArrayList<StatusOrder>>() {}.type
            val statusList: ArrayList<StatusOrder> = gson.fromJson(nowStatusOrder, type) //convert successfully
            statusList.add(StatusOrder(Date(date),statusChoose))// insert new status
            val jsonStatus = gson.toJson(statusList)//convert List to json to update for order
            orderItem.status=jsonStatus

            viewModel.updateOrder(orderItem)//update order
            //if statusChoose=="Order delivered" ,it's mean the Order delivered and we need to create receipt
            if(statusChoose=="Order delivered"){
                CreateReceipt(idOrder,date)
            }
            Toast.makeText(this," Change Status Successful", Toast.LENGTH_SHORT).show()
            back()

    }

    private fun CreateReceipt(idOrder: String,date: String) {
        var receiptEntity=viewModel.getLastReceipt()
        var idReceipt="" //current idReceipt
        var newIdReceipt="" //new idReceipt
        if(receiptEntity!=null){
            idReceipt=receiptEntity.idReceipt
            val numPart=idReceipt.split("recpt_") //get number of id
            var newNum=numPart[1].toInt()+1 //create new number for new id

            if(newNum<10){
                newIdReceipt="recpt_0"+newNum.toString()
            }else if(newNum>=10){
                newIdReceipt="recpt_"+newNum.toString()
            }
            Log.e("!=null","0")
        }else{
            newIdReceipt="recpt_01"
        }
        viewModel.insertReceipt(ReceiptEntity(newIdReceipt,Date(date),idOrder))
    }
}