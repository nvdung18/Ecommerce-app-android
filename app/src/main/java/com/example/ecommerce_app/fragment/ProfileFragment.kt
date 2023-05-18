package com.example.ecommerce_app.fragment

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.contentValuesOf
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activity.EditProfileActivity
import com.example.ecommerce_app.activity.LoginActivity
import com.example.ecommerce_app.activity.OrderActivity
import com.example.ecommerce_app.activity.SplashScreenActivity
import com.example.ecommerce_app.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    val uri_account: Uri = Uri.parse("content://com.example.admin/account")
    val uri_user: Uri = Uri.parse("content://com.example.admin/user")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(LayoutInflater.from(context), container, false)

        loadInfoUser()

        binding.imgMyOrder.setOnClickListener {
            Log.e("a","a")
            var intent=Intent(activity as Context,OrderActivity::class.java)
            startActivity(intent)
        }
        //get id account to delete token and to the Login activity
        binding.imgLogout.setOnClickListener {
            logOut()
        }

        binding.LlProfile.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            getContent.launch(intent)
        }

        return binding.root
    }

    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            loadInfoUser()
        }
    }

    private fun loadInfoUser() {
        val sharedPreferences = context!!.getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        val cursor = context!!.contentResolver?.query(uri_user, null, "token = ?", arrayOf(token), null)
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                val fullName = cursor.getString(cursor.getColumnIndexOrThrow("fullName"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                binding.profileNameProfileFrag.text = fullName.toString().trim()
                binding.profileEmailProfileFrag.text = email.toString().trim()
            }
        }
    }

    private fun logOut() {
        val sharedPreferences = context!!.getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        val cursor = context!!.contentResolver?.query(uri_account, null, "token = ?", arrayOf(token), null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val idAccount = cursor.getString(cursor.getColumnIndexOrThrow("idAccount"))
                Log.e("idAccount", "${idAccount}")
                val token = ""
                val values = ContentValues().apply {
                    put("token", token)
                }
                context!!.contentResolver.update(uri_account, values, "idAccount = ?", arrayOf(idAccount))
                val intent = Intent(context, SplashScreenActivity::class.java)
                startActivity(intent)
            }
            if (cursor != null) {
                cursor.close()
            }

        }
    }
}