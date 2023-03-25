package com.example.admin.presentation.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.admin.Provider
import com.example.admin.R
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.Branch.BranchEntity
import com.example.admin.data.room.Product.ProductEntity
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "Da chay vao")
        var instance=AppDatabase.getInstance(this)

//        var test=findViewById<ImageView>(R.id.imgPgiroduct)
//        var test2=findViewById<TextView>(R.id.txtOverView)
//        test.setOnClickListener {
//            contentResolver.notifyChange(Uri.parse(Provider.URI_TABLE_USER), null)
//            val i=Intent(this,ProductActivity::class.java)
//            startActivity(i)
        }
//        test2.setOnClickListener {
//            Log.e("a","2")
//        }

//        instance.branchDao().deleteAllBranch()
//        instance.branchDao().insertBranch(BranchEntity("idBranch_1","dsfjhjkdf"))

//        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        var f=dateFormat.format(Date("03/12/2002"))
//        instance.branchDao().insertBranch(BranchEntity("${System.currentTimeMillis()}","ahsfha"))
//        val listProduct = mutableListOf<ProductEntity>()
//        for (i in 1..10) {
//            var price = (i*10000).toDouble()
//            val sale = (i*2).toFloat()
//            listProduct.add(
//                ProductEntity("idProduct_${i}",
//                "Bag_${i}",
//                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png",
//                price,
//                "SKLJDKSLjdaskldjaskldjsakldjaskldjaslkdjsalk",
//                "luxury",
//                sale,
//                1231,
//                "idBranch_1"
//            )
//            )
//        }
//        val productDao = AppDatabase.getInstance(this).productDao()
//        productDao.insertAll(listProduct)

    }
