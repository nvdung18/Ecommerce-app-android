package com.example.admin.data.room.Branch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BranchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBranch(branch:BranchEntity)

    @Query("Delete from Branch")
    fun deleteAllBranch()
}