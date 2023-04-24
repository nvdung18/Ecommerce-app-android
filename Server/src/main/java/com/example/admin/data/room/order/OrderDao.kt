package com.example.admin.data.room.order

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface OrderDao {

    @Query("SELECT * FROM OrderTable Order by idOrder desc")
    fun getAllOrder(): LiveData<List<OrderEntity>>

    @Insert()
    fun insertOrder(order:OrderEntity): Long

    @Query("SELECT * FROM OrderTable WHERE idOrder = :idOrder")
    fun getOrderById(idOrder:String):OrderEntity

    @Update
    fun updateOrder(order:OrderEntity)

    @Query("SELECT * FROM OrderTable")
    fun getAllOrderNotLive(): List<OrderEntity>
}