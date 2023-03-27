package com.example.admin.data.room.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.admin.data.room.branch.BranchEntity

@Entity(tableName = "Product", foreignKeys = [ForeignKey(
    entity = BranchEntity::class,
    parentColumns = ["idBranch"],
    childColumns = ["idBranch"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
)])
data class ProductEntity (
    @PrimaryKey() var idProduct: String="",
    @ColumnInfo(name = "nameProduct") var nameProduct: String="",
    @ColumnInfo(name = "image") var image:String="",
    @ColumnInfo(name = "price") var price: Double=0.0,
    @ColumnInfo(name = "description") var description:String="",
    @ColumnInfo(name = "type") var type: String="",
    @ColumnInfo(name = "sale") var sale:Float=0F,
    @ColumnInfo(name = "soldQuantity") var soldQuantity:Int=0,
    @ColumnInfo(name = "idBranch") var idBranch:String=""
)