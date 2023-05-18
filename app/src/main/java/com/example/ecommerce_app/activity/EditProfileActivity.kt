package com.example.ecommerce_app.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    val uri_user: Uri = Uri.parse("content://com.example.admin/user")

    private var name: String = ""
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserInfo()

        binding.editBtn.setOnClickListener {
            getUser()
        }
    }

    private fun loadUserInfo() {
        val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        val cursor = contentResolver?.query(uri_user, null, "token = ?", arrayOf(token), null)
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                val fullName = cursor.getString(cursor.getColumnIndexOrThrow("fullName"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                binding.nameEt.setText(fullName.toString().trim())
                binding.EmailEt.setText(email.toString().trim())
            }
        }
    }

    private fun getUser() {
        name = binding.nameEt.text.toString()
        email = binding.EmailEt.text.toString()
        if(name.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
        } else if(email.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
        } else {
            updateUser()
        }
    }

    private fun updateUser() {
         val values = ContentValues().apply {
             put("name", name)
             put("email", email)
         }
        val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
         val idUpdate = contentResolver?.update(uri_user, values, "idUser = ?", arrayOf(token))
         val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}