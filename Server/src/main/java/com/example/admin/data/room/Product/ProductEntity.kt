package com.example.admin.data.room.Product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.admin.data.room.Branch.BranchEntity

@Entity(tableName = "Product", foreignKeys = [ForeignKey(
    entity = BranchEntity::class,
    parentColumns = ["idBranch"],
    childColumns = ["idBranch"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
)])
class ProductEntity {
    @PrimaryKey() var idProduct: String=""
    @ColumnInfo(name = "nameProduct") var nameProduct: String=""
    @ColumnInfo(name = "image") var image:String=""
    @ColumnInfo(name = "price") var price: Double=0.0
    @ColumnInfo(name = "description") var description:String=""
    @ColumnInfo(name = "type") var type: String=""
    @ColumnInfo(name = "sale") var sale:Float=0F
    @ColumnInfo(name = "soldQuantity") var soldQuantity:Int=0
    @ColumnInfo(name = "idBranch") var idBranch:String=""

    constructor(
        idProduct: String,
        nameProduct: String,
        image: String,
        price: Double,
        description: String,
        type: String,
        sale: Float,
        soldQuantity:Int,
        idBranch: String
    ) {
        this.idProduct = idProduct
        this.nameProduct = nameProduct
        this.image = image
        this.price = price
        this.description = description
        this.type = type
        this.sale = sale
        this.soldQuantity=soldQuantity
        this.idBranch = idBranch
    }
}