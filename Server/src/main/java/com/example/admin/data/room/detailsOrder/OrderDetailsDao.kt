package com.example.admin.data.room.detailsOrder

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDetailsDao {

    @Query("Select * from OrderDetails ")
    fun getAllOrderDetails():LiveData<List<OrderDetailsEntity>>
    @Insert()
    fun insertOrderDetails(orderDetails:OrderDetailsEntity): Long

    @Query("SELECT * FROM OrderDetails Where idOrder=:idOrder")
    fun getDetailsOderById(idOrder:String):List<OrderDetailsEntity>
}