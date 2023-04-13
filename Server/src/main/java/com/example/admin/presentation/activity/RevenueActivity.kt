package com.example.admin.presentation.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.admin.R
import com.example.admin.databinding.ActivityRevenueBinding
import com.example.admin.presentation.fragment.ViewPagerOverviewAdapter
import com.example.admin.presentation.fragment.ViewPagerRevAdapter
import com.google.android.material.tabs.TabLayoutMediator

class RevenueActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRevenueBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revenue)

        binding=ActivityRevenueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter= ViewPagerRevAdapter(supportFragmentManager,lifecycle)
        binding.pagerRev.adapter=adapter
        TabLayoutMediator(binding.tabRev,binding.pagerRev){tab,pos->
            when(pos){
                0->{
                    Log.e("a","1")
                    tab.text="Daily Revenue"}
                1->{
                    Log.e("a","2")
                    tab.text="Weekly Revenue"}
                else->{
                    tab.text="Monthly Revenue"}
                }
            }.attach()
        }

//        val tableLayout = findViewById<TableLayout>(R.id.tbDailyRev) // get the reference to your TableLayout
//        val dataList = mutableListOf<DataItem>()
//
//        // add some data items to the list
//        dataList.add(DataItem("1","1000", "10", "2022-01-01"))
//        dataList.add(DataItem("2","2000", "20", "2022-02-01"))
//        dataList.add(DataItem("3","3000", "30", "2022-03-01"))
//        for (dataItem in dataList) {
//            val tableRow = TableRow(this) // create a new table row
//            tableRow.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
//
//            // create three TextViews to display the three columns of data
//            val order = TextView(this)
//            order.text = dataItem.o
//            order.setPadding(dpToPx(5), 0, 0, 0)
//            tableRow.addView(order)
//
//            // create three TextViews to display the three columns of data
//            val revenueTextView = TextView(this)
//            revenueTextView.text = dataItem.revenue
//            revenueTextView.setPadding(dpToPx(5), 0, 0, 0)
//            tableRow.addView(revenueTextView)
//
//            val quantityTextView = TextView(this)
//            quantityTextView.text = dataItem.quantity
//            quantityTextView.setPadding(dpToPx(5), 0, 0, 0)
//            tableRow.addView(quantityTextView)
//
//            val releaseDateTextView = TextView(this)
//            releaseDateTextView.text = dataItem.releaseDate
//            releaseDateTextView.setPadding(dpToPx(5), 0, 0, 0)
//            tableRow.addView(releaseDateTextView)
//
//            val detailsButton = Button(this)
//            detailsButton.text = "Details"
//            detailsButton.setOnClickListener {
//                // handle button click event here
//                // for example, launch a new activity to show more details about the data item
//            }
//            tableRow.addView(detailsButton)
//
//            // add the new row to the table
//            tableLayout.addView(tableRow)
//        }
//    }
//
//    fun Context.dpToPx(dp: Int): Int {
//        return (dp * resources.displayMetrics.density).toInt()
//    }
//    data class DataItem(val o: String,val revenue: String, val quantity: String, val releaseDate: String)
}