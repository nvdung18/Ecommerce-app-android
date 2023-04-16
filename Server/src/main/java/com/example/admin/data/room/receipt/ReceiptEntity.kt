package com.example.admin.data.room.receipt

import androidx.room.*
import com.example.admin.data.room.ConvertersDate
import com.example.admin.data.room.dailyRev.DailyRevEntity
import com.example.admin.data.room.order.OrderEntity
import java.util.Date

@Entity(tableName = "Receipt", foreignKeys = [ForeignKey(
    entity = OrderEntity::class,
    parentColumns = ["idOrder"],
    childColumns = ["idOrder"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)])
@TypeConverters(ConvertersDate::class)
data class ReceiptEntity (
    @PrimaryKey() var idReceipt:String="",
    @ColumnInfo(name = "releaseDate") var releaseDate:Date= Date("18/10/2003"),
    @ColumnInfo(name = "idOrder") var idOrder:String="",
)