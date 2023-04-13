package com.example.admin.data.room.payment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PaymentDao {

    @Query("Select * from Payment where idPayment = :idPayment")
    fun getPaymentById(idPayment:String):PaymentEntity
    @Insert()
    fun insertPayment(payment:PaymentEntity)
}