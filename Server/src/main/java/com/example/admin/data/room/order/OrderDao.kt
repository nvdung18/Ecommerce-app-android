package com.example.admin.data.room.order

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.admin.data.model.OrderAndOrderdetails
import com.example.admin.data.room.product.ProductEntity

@Dao
interface OrderDao {

    @Query("SELECT * FROM OrderTable Order by idOrder desc")
    fun getAllOrder(): LiveData<List<OrderEntity>>
    @Insert()
    fun insertOrder(order:OrderEntity)

    @Query("SELECT * FROM OrderTable WHERE idOrder = :idOrder")
    fun getOrderById(idOrder:String):OrderEntity

    @Update
    fun updateOrder(order:OrderEntity)

    @Query("Select * from OrderTable join OrderDetails on OrderTable.idOrder = OrderDetails.idOrder join PromoCode on OrderTable.idPromoCode = PromoCode.idPromocode Where idAccount = :idAccount")
    fun getAllOrderByIdJoinOrDetails_App(idAccount:String):List<OrderAndOrderdetails>

}