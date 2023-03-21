package com.example.admin.data.room.DetailsOrder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.admin.data.room.Order.OrderEntity
import com.example.admin.data.room.Product.ProductEntity

@Entity(tableName = "OrderDetails", primaryKeys = ["idOrder","idProduct"], foreignKeys = [ForeignKey(
    entity = OrderEntity::class,
    parentColumns = ["idOrder"],
    childColumns = ["idOrder"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
), ForeignKey(
    entity = ProductEntity::class,
    parentColumns = ["idProduct"],
    childColumns = ["idProduct"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
)])
data class OrderDetailsEntity (
    @ColumnInfo(name = "idOrder") var idOrder:String="",
    @ColumnInfo(name = "idProduct") var idProduct:String="",
    @ColumnInfo(name = "total") var total:Double=0.0,
    @ColumnInfo(name = "quantity") var quantity:Int=0
)