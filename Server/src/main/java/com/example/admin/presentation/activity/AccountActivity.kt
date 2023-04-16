package com.example.admin.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.data.room.account.AccountViewModel
import com.example.admin.databinding.ActivityAccountBinding
import com.example.admin.presentation.adapter.AccountAdapter

class AccountActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAccountBinding
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var adapter:AccountAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        binding= ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        accountViewModel= ViewModelProviders.of(this).get(AccountViewModel::class.java)

        accountViewModel.allAccount.observe(this,{List->
            List?.let {
                adapter.updateList(it)
            }
        })

        initComponents()
    }

    private fun initComponents() {
        adapter= AccountAdapter(this)
        binding.rvAllAccount.adapter=adapter
        binding.rvAllAccount.layoutManager= LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.imgBtnAccountBack.setOnClickListener {
            back()
        }
    }

    private fun back() {
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}