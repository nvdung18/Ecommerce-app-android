

package com.example.admin.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.branch.BranchViewModel
import com.example.admin.databinding.ActivityAddBranchBinding

class AddBranchActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddBranchBinding
    private lateinit var viewModel: BranchViewModel
    private var latestIdBranch:String=""
    private var sizeOfListBranch:Int=0 //to get size of List Branch
    private var newIdBranch:String=""//save new id when we run fun createNewIdBranch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_branch)

//        get data from Intent, particularly here is latest idBranch and depend on that to create new idBranch for new Branch
        val i=intent
        latestIdBranch= i.getStringExtra("latestIdBranch").toString()
        sizeOfListBranch=i.getIntExtra("sizeOfListBranch",0)
        newIdBranch=latestIdBranch
        createNewIDBranch(latestIdBranch) //create new idBranch

        viewModel= ViewModelProviders.of(this).get(BranchViewModel::class.java)

        initComponents()
    }

    private fun createNewIDBranch(latestIdBranch:String) {
//        split id to get number of id
        val numPart=latestIdBranch.split("B")

//        create new id, if sizeOfListBranch==0, we don't need to create new ID because db branch is null, so we just need add into db
        var newNum=numPart[1].toInt()+1
        if(newNum<10 && sizeOfListBranch!=0){
            newIdBranch="B0"+newNum.toString()
        }else if(newNum>=10 && sizeOfListBranch!=0){
            newIdBranch="B"+newNum.toString()
        }
    }

    private fun initComponents() {
        binding= ActivityAddBranchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddBranch.setOnClickListener {
            addBranch()
        }
    }

    private fun addBranch() {
        var nameBranch=binding.edtNameBranch.text
        val branch=BranchEntity(newIdBranch, nameBranch.toString())
        viewModel.insert(branch)
        Toast.makeText(this,"Add Branch Successful", Toast.LENGTH_SHORT).show()

        val intent=Intent(this, BranchActivity::class.java)
        startActivity(intent)
    }
}