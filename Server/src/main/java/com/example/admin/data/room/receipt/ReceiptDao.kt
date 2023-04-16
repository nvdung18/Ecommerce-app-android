package com.example.admin.data.room.receipt

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReceiptDao {

    @Query("Select * from Receipt Order By idReceipt Desc")
    fun getAllReceipt():List<ReceiptEntity>
    @Query("Select * from Receipt Order By idReceipt Desc Limit 1")
    fun getLastReceipt():ReceiptEntity //get last receipt to create new receipt or do something,...
    @Insert
    fun insertReceipt(receiptEntity: ReceiptEntity)
}