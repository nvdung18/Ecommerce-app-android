package com.example.admin.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.admin.Provider
import com.example.admin.R
import com.example.admin.data.room.account.AccountViewModel
import com.example.admin.data.room.branch.BranchViewModel
import com.example.admin.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var provider:Provider
    private lateinit var accountViewModel: AccountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        provider=Provider()

        accountViewModel= ViewModelProviders.of(this).get(AccountViewModel::class.java)

        initComponents()

    }

    private fun initComponents() {

        binding.btnLogin.setOnClickListener {
            var userName=binding.edtUsernameLogin.text.toString()
            var password=binding.edtPasswordLogin.text.toString()

            var hassPass=provider.hashPassword(password)
//            Log.e("account",hassPass)

            var account= accountViewModel.checkLogin(userName,hassPass)
            if(account!=null){
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else{
                Toast.makeText(this," Wrong user name or password", Toast.LENGTH_SHORT).show()
            }

        }
    }


}