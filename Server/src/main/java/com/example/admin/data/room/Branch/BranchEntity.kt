package com.example.admin.data.room.Branch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Branch")
class BranchEntity {
    @PrimaryKey() var idBranch:String=""
    @ColumnInfo(name = "nameBranch") var nameBranch:String=""

    constructor(idBranch: String, nameBranch: String) {
        this.idBranch = idBranch
        this.nameBranch = nameBranch
    }
}