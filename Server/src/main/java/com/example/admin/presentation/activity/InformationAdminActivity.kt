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
import com.example.admin.data.room.user.UserEntity
import com.example.admin.data.room.user.UserViewModel
import com.example.admin.databinding.ActivityEditBranchBinding
import com.example.admin.databinding.ActivityInformationAdminBinding

class InformationAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformationAdminBinding
    private lateinit var admin: UserEntity
    private lateinit var idUser:String
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_admin)

        binding= ActivityInformationAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProviders.of(this).get(UserViewModel::class.java)

        initComponents()
    }

    private fun initComponents() {

        setInformation()

        binding.btnUpdateInforAdmin.setOnClickListener {
            updateInforAdmin()
        }

        binding.imgBtnInformationAdminBack.setOnClickListener {
            back()
        }
    }

    private fun updateInforAdmin() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to update information?")
        builder.setPositiveButton("Yes") { dialog, which ->
            confirmUpdate()
        }
        builder.setNegativeButton("No") { dialog, which ->
            Log.e("a","false")
        }
        builder.show()
    }

    private fun confirmUpdate() {
        var fullName=binding.edtFullnameAdmin.text.toString()
        var gender=binding.edtGenderAdmin.text.toString()
        var address=binding.edtAddressAdmin.text.toString()
        var phoneParseInt:Int=0
        try {
            var phoneNumber=binding.edtPhonenumberAdmin.text.toString()
            phoneParseInt = phoneNumber.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        var email=binding.edtEmailAdmin.text.toString()
        //update
        viewModel.updateBranch(UserEntity(idUser,fullName,gender,address,phoneParseInt,email,1))
        Toast.makeText(this,"Update successful", Toast.LENGTH_SHORT).show()
        back()
    }

    private fun setInformation() {
        admin=viewModel.getAdminByRole()
        idUser=admin.idUser
        binding.edtFullnameAdmin.setText(admin.fullName)
        binding.edtGenderAdmin.setText(admin.gender)
        binding.edtAddressAdmin.setText(admin.address)
        binding.edtPhonenumberAdmin.setText(admin.phoneNumber.toString())
        binding.edtEmailAdmin.setText(admin.email)
    }

    private fun back() {
        var intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}