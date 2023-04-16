package com.example.admin.data.room.promocode

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PromocodeDao {
    @Query("Select * from PromoCode")
    fun getAllPromocode():LiveData<List<PromocodeEntity>>
    @Query("Select * from PromoCode where idPromoCode = :idPromocode")
    fun getPromocodeBiIdServer(idPromocode:String):PromocodeEntity
    @Insert()
    fun insertPromocode(promocode:PromocodeEntity)

    @Delete()
    fun deletePromocode(promocode:PromocodeEntity)

    @Update()
    fun updatePromocode(promocode: PromocodeEntity)
}