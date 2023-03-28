package com.example.admin.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.admin.data.room.account.AccountDao
import com.example.admin.data.room.account.AccountEntity
import com.example.admin.data.room.branch.BranchDao
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.cart.CartDao
import com.example.admin.data.room.cart.CartEntity
import com.example.admin.data.room.cartDetails.CartDetailsDao
import com.example.admin.data.room.cartDetails.CartDetailsEntity
import com.example.admin.data.room.dailyRev.DailyRevDao
import com.example.admin.data.room.dailyRev.DailyRevEntity
import com.example.admin.data.room.detailsOrder.OrderDetailsDao
import com.example.admin.data.room.detailsOrder.OrderDetailsEntity
import com.example.admin.data.room.monthlyRev.MonthlyRevDao
import com.example.admin.data.room.monthlyRev.MonthlyRevEntity
import com.example.admin.data.room.order.OrderDao
import com.example.admin.data.room.order.OrderEntity
import com.example.admin.data.room.payment.PaymentDao
import com.example.admin.data.room.payment.PaymentEntity
import com.example.admin.data.room.product.ProductDao
import com.example.admin.data.room.product.ProductEntity
import com.example.admin.data.room.promocode.PromocodeDao
import com.example.admin.data.room.promocode.PromocodeEntity
import com.example.admin.data.room.receipt.ReceiptDao
import com.example.admin.data.room.receipt.ReceiptEntity
import com.example.admin.data.room.user.UserDao
import com.example.admin.data.room.user.UserEntity
import com.example.admin.data.room.weeklyRev.WeeklyRevDao
import com.example.admin.data.room.weeklyRev.WeeklyRevEntity
import com.example.admin.data.room.checkout.CheckoutDao
import com.example.admin.data.room.checkout.CheckoutEntity

@Database(entities = [AccountEntity::class, BranchEntity::class, CartEntity::class, CartDetailsEntity::class, CheckoutEntity::class,DailyRevEntity::class,
    OrderDetailsEntity::class, MonthlyRevEntity::class, OrderEntity::class, PaymentEntity::class, ProductEntity::class, PromocodeEntity::class, ReceiptEntity::class,
    UserEntity::class, WeeklyRevEntity::class] , version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun accountDao():AccountDao
    abstract fun branchDao(): BranchDao
    abstract fun cartDao():CartDao
    abstract fun cartDetailsDao():CartDetailsDao
    abstract fun checkoutDao():CheckoutDao
    abstract fun dailyRevDao():DailyRevDao
    abstract fun orderDetailsDao(): OrderDetailsDao
    abstract fun monthlyRevDao():MonthlyRevDao
    abstract fun orderDao():OrderDao
    abstract fun paymentDao():PaymentDao
    abstract fun productDao():ProductDao
    abstract fun promocodeDao():PromocodeDao
    abstract fun receiptDao():ReceiptDao
    abstract fun userDao():UserDao
    abstract fun weeklyRevDao():WeeklyRevDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(AppDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "test10"
                ).allowMainThreadQueries().build()

                INSTANCE = instance
                return instance
            }
        }

        fun deleteAllData(context: Context) {
            val data = getInstance(context)
            data.clearAllTables()
        }
    }
}
