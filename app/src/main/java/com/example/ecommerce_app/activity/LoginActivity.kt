package com.example.ecommerce_app.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityLoginBinding
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {

    private var name: String = ""
    private var password: String = ""
    val TAG = "LOGINACTIVITY"
    val uri_account: Uri = Uri.parse("content://com.example.admin/account")

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            name = binding.nameEt.text.toString().trim()
            password = binding.PassEt.text.toString().trim()

            if(name.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Email is empty", Toast.LENGTH_SHORT).show()
            } else if(password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Password is empty", Toast.LENGTH_SHORT).show()
            } else {
                if(checkLogin(name, password)) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
            }
        }

    }

    private fun checkLogin(name: String, password: String): Boolean {
        val cursor = contentResolver.query(uri_account, null, "userName = ? and password = ?", arrayOf(name, password), null)
        Toast.makeText(this, "${cursor}", Toast.LENGTH_SHORT).show()
        if(cursor != null && cursor.moveToFirst()) {
            val token = createRandomToken(6)
            val values = ContentValues().apply {
                put("token", token)
            }
            val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("token", token)
            editor.apply()
//            var listAccount = cursor.getString(cursor.getColumnIndexOrThrow("idAccount")).split("_")
            Log.d(TAG, "${values?.getAsString("token").toString()}|${cursor.getString(cursor.getColumnIndexOrThrow("idAccount"))}")
            val uri = Uri.parse("content://com.example.admin/account")
            val updateToken = contentResolver.update(uri, values,"idAccount = ?",arrayOf(cursor.getString(cursor.getColumnIndexOrThrow("idAccount"))))
            cursor.close()
            return true
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            cursor!!.close()
            return false
        }
    }
    private fun createRandomToken(length: Int): String {
        val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val random = Random(System.currentTimeMillis())
        return  (1..length)
            .map { chars[random.nextInt(0, chars.length)] }
            .joinToString("")
    }

}