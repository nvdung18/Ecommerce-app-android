package com.example.admin.data.room.Branch

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BranchDao {
    @Insert()
    fun insertBranch(branch:BranchEntity)
}