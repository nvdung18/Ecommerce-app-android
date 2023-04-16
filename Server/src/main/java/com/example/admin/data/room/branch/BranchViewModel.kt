package com.example.admin.data.room.branch

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.admin.data.model.Branch
import com.example.admin.data.model.BrandAndModel
import com.example.admin.data.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BranchViewModel(application: Application) : AndroidViewModel(application) {
    private val branchDao:BranchDao
    var allBranch:LiveData<List<BranchEntity>>
    init {
        branchDao= AppDatabase.getInstance(application).branchDao()
        allBranch=branchDao.getAllBranch()
    }

    fun insert(branch:BranchEntity)=viewModelScope.launch ( Dispatchers.IO ){
        branchDao.insertBranch(branch)
    }

    fun updateBranch(branch:BranchEntity)=viewModelScope.launch (Dispatchers.IO){
        branchDao.updateBranch(branch)
    }

    fun deleteBranch(branch: BranchEntity)=viewModelScope.launch (Dispatchers.IO){
        branchDao.deleteBranch(branch)
    }

    fun getProductAndBranchBtId(idBranch: String):List<BrandAndModel>{
        return branchDao.getProductAndBranchById(idBranch)
    }

}