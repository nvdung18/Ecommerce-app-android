package com.example.ecommerce_app.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activity.LoginActivity
import com.example.ecommerce_app.activity.OrderActivity
import com.example.ecommerce_app.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(LayoutInflater.from(context), container, false)

        binding.imgMyOrder.setOnClickListener {
            Log.e("a","a")
            var intent=Intent(activity as Context,OrderActivity::class.java)
            startActivity(intent)
        }

//        binding.imgSettingProfile.setOnClickListener {
//            var intent=Intent(activity as Context,LoginActivity::class.java)
//            startActivity(intent)
//        }
        // Inflate the layout for this fragment
        return binding.root
    }
}