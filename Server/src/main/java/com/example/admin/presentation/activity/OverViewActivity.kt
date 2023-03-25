package com.example.admin.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.admin.R
import com.example.admin.databinding.ActivityAddBranchBinding
import com.example.admin.databinding.ActivityOverViewBinding
import com.example.admin.presentation.fragment.ViewPagerOverviewAdapter
import com.google.android.material.tabs.TabLayoutMediator
//import kotlinx.android.synthetic.main.activity_over_view.*

class OverViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityOverViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_over_view)

        binding= ActivityOverViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter=ViewPagerOverviewAdapter(supportFragmentManager,lifecycle)
        binding.pagerTest.adapter=adapter
        TabLayoutMediator(binding.tabOverView,binding.pagerTest){tab,pos->
            when(pos){
                0->{
                    Log.e("a","1")
                    tab.text="Sale"}
                1->{
                    Log.e("a","2")
                    tab.text="Payment"}

            }
        }.attach()
    }
}