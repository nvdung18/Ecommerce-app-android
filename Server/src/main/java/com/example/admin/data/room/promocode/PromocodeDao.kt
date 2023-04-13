package com.example.admin.data.room.promocode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PromocodeDao {

    @Query("Select * from PromoCode where idPromoCode = :idPromocode")
    fun getPromocodeBiIdServer(idPromocode:String):PromocodeEntity
    @Insert()
    fun insertPromocode(promocode:PromocodeEntity)
}