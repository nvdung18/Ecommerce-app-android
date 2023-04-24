package com.example.ecommerce_app.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityPaymentMethodBinding

class PaymentMethodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentMethodBinding
    private var idPayment = "idPayment2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgMomoCard.setOnClickListener {
            val intent = Intent()
            intent.putExtra("idpayment", idPayment)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.backIvPaymentMethodsPage.setOnClickListener {
            onBackPressed()
        }
    }
}