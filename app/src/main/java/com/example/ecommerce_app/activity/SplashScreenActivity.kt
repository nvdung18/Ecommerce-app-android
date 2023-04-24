package com.example.ecommerce_app.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    val uri_account: Uri = Uri.parse("content://com.example.admin/account")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed(Runnable {
            checkUser()
        }, 2000)
    }

    private fun checkUser() {
        val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        Log.d("TOKEN", "${token}")
        if(token != "") {
            val cursor = contentResolver.query(uri_account, null, "token = ?", arrayOf(token), null)
            if(cursor != null && cursor.moveToFirst()) {
                val intent = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            } else {
                Log.d("CUROSR", "null")
            }
        } else if(token == "") {
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}