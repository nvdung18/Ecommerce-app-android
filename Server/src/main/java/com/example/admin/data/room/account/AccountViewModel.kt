package com.example.admin.data.room.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.admin.data.room.AppDatabase

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val accountDao:AccountDao
    private val instance= AppDatabase.getInstance(application)
    var allAccount:LiveData<List<AccountEntity>>
    init {
        accountDao=instance.accountDao()
        allAccount=accountDao.getAllAccountSer()
    }

    fun checkLogin(userName:String,password:String):AccountEntity{
        return accountDao.queryAccountByUserNameAndPW(userName,password)
    }
}