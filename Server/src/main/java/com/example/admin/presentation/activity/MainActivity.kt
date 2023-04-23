package com.example.admin.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.admin.R
import com.example.admin.data.room.AppDatabase
import com.example.admin.databinding.ActivityMainBinding
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()

//        AppDatabase.deleteAllData(this)

//        test2.setOnClickListener {
//            Log.e("a","2")
//        }

//        instance.branchDao().insertBranch(BranchEntity("${System.currentTimeMillis()}","dsfjhjkdf"))
//
//        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        var f=dateFormat.format(Date("03/12/2002"))
//        instance.branchDao().insertBranch(BranchEntity("${System.currentTimeMillis()}","ahsfha"))

    }

    private fun initComponents() {

        // ImageView here like a button to transfer to new Activity
        binding.imgProduct.setOnClickListener {
            productActivity()
        }

        binding.imgOverView.setOnClickListener {
            overViewActivity()
        }

        binding.imgBranch.setOnClickListener {
            branchActivity()
        }

        binding.imgOrder.setOnClickListener {
            orderActivity()
        }

        binding.imgReceipt.setOnClickListener {
            receiptActivity()
        }

        binding.imgRev.setOnClickListener {
            revenueActivity()
        }

        binding.imgPromocode.setOnClickListener {
            promocodeActivity()
        }

        binding.imgLogout.setOnClickListener {
            loginActivity()
        }

        binding.imgAccount.setOnClickListener {
            accountActivity()
        }
    }

    private fun accountActivity() {
        var i = Intent(this,AccountActivity::class.java)
        startActivity(i)
    }

    private fun loginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun promocodeActivity() {
        var i = Intent(this,PromoCodeActivity::class.java)
        startActivity(i)
    }

    private fun revenueActivity() {
        var i = Intent(this,RevenueActivity::class.java)
        startActivity(i)
    }

    private fun branchActivity() {
        var i = Intent(this,BranchActivity::class.java)
        startActivity(i)
    }

    private fun overViewActivity() {
        val i=Intent(this,OverViewActivity::class.java)
        startActivity(i)
    }

    private fun productActivity() {
        val i=Intent(this,ProductActivity::class.java)
        startActivity(i)
    }

    private fun orderActivity(){
        val i=Intent(this,OrderActivity::class.java)
        i.putExtra("fun","Order")//to determine we will click on order or receipt to show correct interface
                                            //, because order and receipt use same the activity, just a little bit change about interface
        startActivity(i)
    }

    private fun receiptActivity() {
        var i = Intent(this,OrderActivity::class.java)
        i.putExtra("fun","Receipt")//to determine we will click on order or receipt to show correct interface
                                            //, because order and receipt use same the activity, just a little bit change about interface
        startActivity(i)
    }
}