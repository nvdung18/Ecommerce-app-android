package com.example.admin.data.room.branch

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BranchDao {
    @Query("SELECT * FROM Branch order by idBranch desc")
    fun getAllBranch(): LiveData<List<BranchEntity>>

    @Insert()
    fun insertBranch(branch:BranchEntity)

    @Query("SELECT * FROM Branch order by idBranch asc")
    fun getAllBranchOrderASC():LiveData<List<BranchEntity>>
    
     @Query("SELECT * FROM Branch order by idBranch desc")
    fun getAllBranchNotLive(): List<BranchEntity>
}

