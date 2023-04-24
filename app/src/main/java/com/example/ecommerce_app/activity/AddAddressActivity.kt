package com.example.ecommerce_app.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityAddAddressBinding


class AddAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAddressBinding
    private var desaddress = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            desaddress = binding.desAddress.text.toString().trim()
            if(desaddress.isNotEmpty()) {
                val intent = Intent()
                intent.putExtra("address", desaddress)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Empty Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.backImg.setOnClickListener {
            onBackPressed()
        }
    }
}