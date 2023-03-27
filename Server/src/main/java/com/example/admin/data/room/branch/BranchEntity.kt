package com.example.admin.data.room.branch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Branch")
data class BranchEntity (
    @PrimaryKey() var idBranch:String="",
    @ColumnInfo(name = "nameBranch") var nameBranch:String=""
)