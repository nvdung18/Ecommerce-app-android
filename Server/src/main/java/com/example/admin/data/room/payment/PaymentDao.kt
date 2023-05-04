package com.example.admin.data.room.payment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PaymentDao {

    @Query("Select * from Payment where idPayment = :idPayment")
    fun getPaymentById(idPayment:String):PaymentEntity

    @Insert()
    fun insertPayment(payment:PaymentEntity)

    @Update
    fun updatePayment(payment:PaymentEntity)

    @Query("Select * from Payment where namePayment = :namePayment")
    fun getPaymentByName(namePayment:String):PaymentEntity
}