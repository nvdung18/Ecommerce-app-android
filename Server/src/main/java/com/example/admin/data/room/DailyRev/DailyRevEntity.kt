package com.example.admin.data.room.DailyRev

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.admin.data.room.ConvertersDate
import java.util.*

@Entity(tableName = "DailyRev")
@TypeConverters(ConvertersDate::class)
class DailyRevEntity {
    @PrimaryKey(autoGenerate = true)
    var idDayRev:Int=0

    @ColumnInfo(name = "releaseDate") var releaseDate: Date = Date("18/10/2003")
    @ColumnInfo(name = "revenue") var revenue:Double=0.0
    @ColumnInfo(name = "quantity") var quantity:Int=0
}