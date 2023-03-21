package com.example.ecommerce_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}