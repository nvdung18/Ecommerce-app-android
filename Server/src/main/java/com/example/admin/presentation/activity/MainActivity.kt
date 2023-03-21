package com.example.admin.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.admin.R
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.Branch.BranchEntity
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "Da chay vao")
        var instance=AppDatabase.getInstance(this)

        var test=findViewById<ImageView>(R.id.imgProduct)
//        var test2=findViewById<TextView>(R.id.txtOverView)
        test.setOnClickListener {
            val i=Intent(this,ProductActivity::class.java)
            startActivity(i)
        }

        imgOverView.setOnClickListener {
            val i=Intent(this,OverViewActivity::class.java)
            startActivity(i)
        }
//        test2.setOnClickListener {
//            Log.e("a","2")
//        }

        instance.branchDao().insertBranch(BranchEntity("${System.currentTimeMillis()}","dsfjhjkdf"))

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var f=dateFormat.format(Date("03/12/2002"))
        instance.branchDao().insertBranch(BranchEntity("${System.currentTimeMillis()}","ahsfha"))

    }
}