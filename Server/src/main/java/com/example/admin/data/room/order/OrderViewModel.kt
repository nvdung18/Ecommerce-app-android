package com.example.admin.data.room.order

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.admin.data.model.StatusOrder
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.checkout.CheckoutDao
import com.example.admin.data.room.checkout.CheckoutEntity
import com.example.admin.data.room.detailsOrder.OrderDetailsDao
import com.example.admin.data.room.detailsOrder.OrderDetailsEntity
import com.example.admin.data.room.payment.PaymentDao
import com.example.admin.data.room.payment.PaymentEntity
import com.example.admin.data.room.promocode.PromocodeDao
import com.example.admin.data.room.promocode.PromocodeEntity
import com.example.admin.data.room.receipt.ReceiptDao
import com.example.admin.data.room.receipt.ReceiptEntity
import dagger.hilt.android.scopes.ViewScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var orderDao:OrderDao
    private lateinit var checkoutDao:CheckoutDao
    private lateinit var orderDetailsDao:OrderDetailsDao
    private lateinit var receiptDao:ReceiptDao
    private lateinit var promocodeDao: PromocodeDao
    private lateinit var paymentDao: PaymentDao
    private lateinit var instance:AppDatabase
    lateinit var allOrder:LiveData<List<OrderEntity>>

    init {
        instance= AppDatabase.getInstance(application)
        orderDao=instance.orderDao()
        checkoutDao=instance.checkoutDao()
        orderDetailsDao=instance.orderDetailsDao()
        receiptDao=instance.receiptDao()
        promocodeDao=instance.promocodeDao()
        paymentDao=instance.paymentDao()

        allOrder=orderDao.getAllOrder()
    }

    fun updateOrder(orderEntity: OrderEntity)=viewModelScope.launch( Dispatchers.IO ) {
        orderDao.updateOrder(orderEntity)
    }

    fun getOrderById(idOrder:String):OrderEntity {
        return orderDao.getOrderById(idOrder)
    }

    fun getCheckoutById(idCheckout:String):CheckoutEntity{
        return checkoutDao.getCheckoutById(idCheckout)
    }

    fun getOrderDetailsById(idOrder: String):List<OrderDetailsEntity>{
        return orderDetailsDao.getDetailsOderById(idOrder)
    }

    fun getLastReceipt():ReceiptEntity{
        return receiptDao.getLastReceipt()
    }
    fun insertReceipt(receiptEntity: ReceiptEntity){
        receiptDao.insertReceipt(receiptEntity)
    }

    fun getAllReceipt():List<ReceiptEntity>{
        return receiptDao.getAllReceipt()
    }

    fun getPromocodeById(idPromocode:String):PromocodeEntity{
        return promocodeDao.getPromocodeBiIdServer(idPromocode)
    }

    fun getPaymentById(idPayment: String):PaymentEntity{
        return paymentDao.getPaymentById(idPayment)
    }
}