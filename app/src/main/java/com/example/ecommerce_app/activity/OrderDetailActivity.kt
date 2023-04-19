package com.example.ecommerce_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityOrderDetailBinding
import com.example.ecommerce_app.fragment.OrderUserFragment

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backImg.setOnClickListener {
            back()
        }
    }

    private fun back() {
        var intent=Intent(this,OrderActivity::class.java)
        startActivity(intent)
    }
}