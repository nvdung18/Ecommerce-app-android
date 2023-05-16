package com.example.admin.data.room.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.account.AccountDao
import com.example.admin.data.room.account.AccountEntity
import com.example.admin.data.room.branch.BranchEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao
    private val instance= AppDatabase.getInstance(application)
    init {
        userDao=instance.userDao()
    }
    fun getAdminByRole(): UserEntity {
        return userDao.getAdminByRole()
    }

    fun updateBranch(user:UserEntity)=viewModelScope.launch (Dispatchers.IO){
        userDao.updateAdmin(user)
    }
}