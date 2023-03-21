package com.example.ecommerce_app.activity

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivitySignUpBinding
import com.google.android.material.tabs.TabLayout.TabGravity
import java.util.regex.Pattern
import kotlin.random.Random

class SignUpActivity : AppCompatActivity() {

    val Tag = "SIGNUPACTIVITY"
    private lateinit var binding: ActivitySignUpBinding
    private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var confirm_password: String = ""
    lateinit var progressDialog: ProgressDialog
    val uri_user: Uri = Uri.parse("content://com.example.admin/user")
    val uri_account: Uri = Uri.parse("content://com.example.admin/account")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init progress bar
        progressDialog = ProgressDialog(this@SignUpActivity)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click signup
        binding.signUpBtnSignUpPage.setOnClickListener {
            name = binding.nameEtSignUpPage.text.toString().trim()
            email = binding.emailEtSignUpPage.text.toString().trim()
            password = binding.PassEtSignUpPage.text.toString().trim()
            confirm_password = binding.cPassEtSignUpPage.text.toString().trim()

            if(name.isEmpty()) {
              Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
            } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            } else if(password.isEmpty()) {
                Toast.makeText(this, "Enter you password", Toast.LENGTH_SHORT).show()
            } else if(password != confirm_password) {
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
            } else {
                if(!checkEmailSimiliar(email)) {
                    createUserAccount()
                } else {
                    Toast.makeText(this, "Email is exist", Toast.LENGTH_SHORT).show()
                }
            }
            progressDialog.dismiss()
        }

        binding.signInTvSignUpPage.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    private fun checkEmailSimiliar(email: String): Boolean {
        var cursor = contentResolver.query(uri_user, null, null, null, null)
        if(cursor != null && cursor.moveToFirst()) {
            do {
                if(email == cursor.getString(cursor.getColumnIndexOrThrow("email"))) {
                   return true
                }
            } while (cursor.moveToNext())
        }
        if(cursor != null) {
            cursor.close()
        }
        return false
    }

    private fun createUserAccount() {
        progressDialog.setMessage("Creating account")
        progressDialog.show()
        val values_user = ContentValues().apply {
            put("name", name)
            put("email", email)
        }

        val values_account = ContentValues().apply {
            put("userName", name)
            put("password", password)
//            put("token", createRandomToken(6))
        }

        var uri_user_id = contentResolver.insert(uri_user, values_user)
        var uri_account_id = contentResolver.insert(uri_account, values_account)
        Toast.makeText(this, "Success to create an account", Toast.LENGTH_SHORT).show()

    }

    private fun createRandomToken(length: Int): String {
        val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val random = Random(System.currentTimeMillis())
        return  (1..length)
            .map { chars[random.nextInt(0, chars.length)] }
            .joinToString("")
    }
}