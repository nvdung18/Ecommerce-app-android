package com.example.admin.data.room.promocode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.admin.data.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PromocodeViewModel(application: Application):AndroidViewModel(application) {
    private var application=application
    private lateinit var promocodeDao: PromocodeDao
    private lateinit var instance: AppDatabase
    lateinit var allPromocode:LiveData<List<PromocodeEntity>>

    init {
        instance = AppDatabase.getInstance(application)
        promocodeDao=instance.promocodeDao()

        allPromocode=promocodeDao.getAllPromocode()
    }

    fun insertPromocode(promocode:PromocodeEntity)=viewModelScope.launch(Dispatchers.IO) {
        promocodeDao.insertPromocode(promocode)
    }

    fun deletePromocode(promocode:PromocodeEntity)=viewModelScope.launch(Dispatchers.IO) {
        promocodeDao.deletePromocode(promocode)
    }

    fun updatePromocode(promocode:PromocodeEntity)=viewModelScope.launch(Dispatchers.IO) {
        promocodeDao.updatePromocode(promocode)
    }
}