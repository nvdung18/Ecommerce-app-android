package com.example.admin.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.admin.data.room.Account.AccountDao
import com.example.admin.data.room.Account.AccountEntity
import com.example.admin.data.room.Branch.BranchDao
import com.example.admin.data.room.Branch.BranchEntity
import com.example.admin.data.room.Cart.CartDao
import com.example.admin.data.room.Cart.CartEntity
import com.example.admin.data.room.CartDetails.CartDetailsDao
import com.example.admin.data.room.CartDetails.CartDetailsEntity
import com.example.admin.data.room.DailyRev.DailyRevDao
import com.example.admin.data.room.DailyRev.DailyRevEntity
import com.example.admin.data.room.DetailsOrder.OrderDetailsDao
import com.example.admin.data.room.DetailsOrder.OrderDetailsEntity
import com.example.admin.data.room.MonthlyRev.MonthlyRevDao
import com.example.admin.data.room.MonthlyRev.MonthlyRevEntity
import com.example.admin.data.room.Order.OrderDao
import com.example.admin.data.room.Order.OrderEntity
import com.example.admin.data.room.Payment.PaymentDao
import com.example.admin.data.room.Payment.PaymentEntity
import com.example.admin.data.room.Product.ProductDao
import com.example.admin.data.room.Product.ProductEntity
import com.example.admin.data.room.Promocode.PromocodeDao
import com.example.admin.data.room.Promocode.PromocodeEntity
import com.example.admin.data.room.Receipt.ReceiptDao
import com.example.admin.data.room.Receipt.ReceiptEntity
import com.example.admin.data.room.User.UserDao
import com.example.admin.data.room.User.UserEntity
import com.example.admin.data.room.WeeklyRev.WeeklyRevDao
import com.example.admin.data.room.WeeklyRev.WeeklyRevEntity
import com.example.admin.data.room.checkout.CheckoutDao
import com.example.admin.data.room.checkout.CheckoutEntity

@Database(entities = [AccountEntity::class, BranchEntity::class, CartEntity::class, CartDetailsEntity::class, CheckoutEntity::class,DailyRevEntity::class,
    OrderDetailsEntity::class, MonthlyRevEntity::class, OrderEntity::class, PaymentEntity::class, ProductEntity::class, PromocodeEntity::class, ReceiptEntity::class,
    UserEntity::class, WeeklyRevEntity::class] , version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun accountDao():AccountDao
    abstract fun branchDao():BranchDao
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
                    "test"
                ).allowMainThreadQueries().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}