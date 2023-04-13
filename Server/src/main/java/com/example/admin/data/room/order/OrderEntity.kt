package com.example.admin.data.room.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.admin.data.room.account.AccountEntity
import com.example.admin.data.room.payment.PaymentEntity
import com.example.admin.data.room.promocode.PromocodeEntity
import com.example.admin.data.room.checkout.CheckoutEntity

@Entity(tableName = "OrderTable", foreignKeys = [ForeignKey(
    entity = AccountEntity::class,
    parentColumns = ["idAccount"],
    childColumns = ["idAccount"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
), ForeignKey(
    entity = PaymentEntity::class,
    parentColumns = ["idPayment"],
    childColumns = ["idPayment"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
), ForeignKey(
    entity = PromocodeEntity::class,
    parentColumns = ["idPromoCode"],
    childColumns = ["idPromoCode"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
), ForeignKey(
    entity = CheckoutEntity::class,
    parentColumns = ["idCheckout"],
    childColumns = ["idCheckout"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
)])
data class OrderEntity (
    @PrimaryKey() var idOrder:String="",
    @ColumnInfo(name = "status") var status:String="",
//    @ColumnInfo(name = "deliveryTime") var deliveryTime:String="",
    @ColumnInfo(name = "orderNotes") var orderNotes:String="",
    @ColumnInfo(name = "deliveryCharges") var deliveryCharges:Double=0.0,
    @ColumnInfo(name = "productMoney") var productMoney:Double=0.0,
    @ColumnInfo(name = "idAccount") var idAccount:String="",
    @ColumnInfo(name = "idPayment") var idPayment:String="",
    @ColumnInfo(name = "idPromoCode") var idPromoCode:String="",
    @ColumnInfo(name = "idCheckout") var idCheckout:String=""
)