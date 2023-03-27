package com.example.admin.data.room.monthlyRev

import androidx.room.*
import com.example.admin.data.room.ConvertersDate
import com.example.admin.data.room.dailyRev.DailyRevEntity
import java.util.*
@Entity(tableName = "MonthlyRev", foreignKeys = [ForeignKey(
    entity = DailyRevEntity::class,
    parentColumns = ["idDayRev"],
    childColumns = ["idDayRev"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)])
@TypeConverters(ConvertersDate::class)
data class MonthlyRevEntity (
    @PrimaryKey(autoGenerate = true) var idMonthRev:Int=0,
    @ColumnInfo(name = "releaseDate") var releaseDate: Date = Date("18/10/2003"),
    @ColumnInfo(name = "revenue") var revenue:Double=0.0,
    @ColumnInfo(name = "quantity") var quantity:Int=0,
    @ColumnInfo(name = "idDayRev") var idDayRev:Int=0
)