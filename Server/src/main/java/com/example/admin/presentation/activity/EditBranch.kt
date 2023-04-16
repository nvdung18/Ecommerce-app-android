package com.example.admin.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.branch.BranchViewModel
import com.example.admin.databinding.ActivityEditBranchBinding
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class EditBranch : AppCompatActivity() {
    private lateinit var binding:ActivityEditBranchBinding
    private lateinit var branchItem: BranchEntity
    private lateinit var viewModel: BranchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_branch)

        binding= ActivityEditBranchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()

        viewModel= ViewModelProviders.of(this).get(BranchViewModel::class.java)
    }

    private fun initComponents() {
        var i=intent
        var branch=i.getStringExtra("branch") // now branch is json
        val gson = Gson()//gson to transfer json to Object BranchEntity
        val type = object : TypeToken<BranchEntity>() {}.type
        branchItem = gson.fromJson(branch, type)

        // get infor and show it in interface
        binding.edtNameBranch.setText(branchItem.nameBranch.toString())

        binding.imgBtnEditBranchBack.setOnClickListener {
            back()
        }

        binding.btnUpdateBranch.setOnClickListener {
            updateBranch()
        }
    }

    private fun updateBranch() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to update branch?")
        builder.setPositiveButton("Yes") { dialog, which ->
            confirmUpdate()
        }
        builder.setNegativeButton("No") { dialog, which ->
            Log.e("a","false")
        }
        builder.show()
    }

    private fun confirmUpdate() {
        var nameBranch=binding.edtNameBranch.text.toString()
        branchItem.nameBranch=nameBranch
        //update
        viewModel.updateBranch(branchItem)
        Toast.makeText(this,"Update successful", Toast.LENGTH_SHORT).show()
        back()
    }

    private fun back() {
        var intent= Intent(this,BranchActivity::class.java)
        startActivity(intent)
    }


}