package com.example.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.Branch.BranchEntity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var instance=AppDatabase.getInstance(this)

        instance.branchDao().insertBranch(BranchEntity("br2","dsfjhjkdf"))

//        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        var f=dateFormat.format(Date("03/12/2002"))
//        instance.receiptDao().insertReceipt(Receipt(Date(f)))

    }
}