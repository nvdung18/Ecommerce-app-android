package com.example.ecommerce_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityResetPassWordBinding

class ResetPassWordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPassWordBinding
    private var password: String = ""
    private var confirm_password: String = ""
    private var number: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPassWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myNumber = intent.getStringExtra("numberRandom")
        val myEmail = intent.getStringExtra("email")

        binding.saveBtn.setOnClickListener {
            number = binding.numberEt.text.toString().trim()
            password = binding.passwordEt.text.toString().trim()
            confirm_password = binding.confirmPassEt.text.toString().trim()
            if(number.isEmpty()) {
                Toast.makeText(this, "Number is empty", Toast.LENGTH_SHORT).show()
            } else if(number != myNumber) {
                Toast.makeText(this, "Number is not be similiar to code", Toast.LENGTH_SHORT).show()
            } else if(password.isEmpty()) {
                Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show()
            } else if(confirm_password.isEmpty()){
                Toast.makeText(this, "Confirm password is empty", Toast.LENGTH_SHORT).show()
            } else if(confirm_password != password) {
                Toast.makeText(this, "Confirmpassword is not be similar to Password", Toast.LENGTH_SHORT).show()
            } else {
                updatePassword(myEmail)
            }
        }
    }

    private fun updatePassword(myEmail: String?) {

    }
}