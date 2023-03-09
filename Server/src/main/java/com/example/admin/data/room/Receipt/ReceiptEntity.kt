package com.example.admin.data.room.Receipt

import androidx.room.*
import com.example.admin.data.room.ConvertersDate
import com.example.admin.data.room.DailyRev.DailyRevEntity
import com.example.admin.data.room.Order.OrderEntity
import java.util.Date

@Entity(tableName = "Receipt", foreignKeys = [ForeignKey(
    entity = OrderEntity::class,
    parentColumns = ["idOrder"],
    childColumns = ["idOrder"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
), ForeignKey(
    entity = DailyRevEntity::class,
    parentColumns = ["idDayRev"],
    childColumns = ["idDayRev"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)])
@TypeConverters(ConvertersDate::class)
class ReceiptEntity {
    @PrimaryKey() var idReceipt:String=""
    @ColumnInfo(name = "releaseDate") var releaseDate:Date= Date("18/10/2003")
    @ColumnInfo(name = "idOrder") var idOrder:String=""
    @ColumnInfo(name = "idDayRev") var idDayRev:Int=0


}