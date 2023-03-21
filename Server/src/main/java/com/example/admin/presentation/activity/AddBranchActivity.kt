

package com.example.admin.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.admin.R
import com.example.admin.databinding.ActivityAddBranchBinding

class AddBranchActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddBranchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_branch)

        initComponents()
    }

    private fun initComponents() {
        binding= ActivityAddBranchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddBranch.setOnClickListener {
            addBranch()
        }
    }

    private fun addBranch() {
        var idBranch=binding.edtIdBranch.text
        var nameBranch=binding.edtNameBranch.text


    }
}