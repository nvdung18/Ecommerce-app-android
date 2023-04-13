package com.example.admin.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.branch.BranchViewModel
import com.example.admin.databinding.ActivityBranchBinding
import com.example.admin.presentation.adapter.BranchAdapter
import com.example.admin.presentation.adapter.BranchItemClickAdapter
import com.google.gson.Gson

class BranchActivity : AppCompatActivity(), BranchItemClickAdapter {
    private lateinit var binding:ActivityBranchBinding
    private lateinit var viewModel: BranchViewModel
    private lateinit var adapter:BranchAdapter
    private var latestIdBranch:String=""//to get latest  branch to get
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch)

        binding= ActivityBranchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()

        viewModel=ViewModelProviders.of(this).get(BranchViewModel::class.java)

        viewModel.allBranch.observe(this,{List->
            List?.let {
                adapter.updateList(it)
            }
        })

    }

    private fun initComponents() {
        adapter= BranchAdapter(this)
        adapter.setListener(this)
        binding.rvAllBranch.adapter=adapter
        binding.rvAllBranch.layoutManager= LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.btnAddBranch.setOnClickListener {
            addBranchActivity()
        }

        binding.imgBtnBranchBack.setOnClickListener {
            back()
        }

//        addSampleBranch()
    }

    private fun addBranchActivity() {
//        viewModel.insert(BranchEntity("b08","asdjfjasf"))
        var listBranch=viewModel.allBranch.value

        if (listBranch != null && listBranch.size>0) {
            latestIdBranch= listBranch!![0].idBranch
        }else{
            latestIdBranch="B01"
        }
        var intent=Intent(this,AddBranchActivity::class.java)
        intent.putExtra("latestIdBranch",latestIdBranch)
        intent.putExtra("sizeOfListBranch",listBranch!!.size)
        startActivity(intent)

    }

    private fun back() {
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun addSampleBranch(){
        var branchDao=AppDatabase.getInstance(this).branchDao()
        val branchList= mutableListOf<BranchEntity>()

        branchList.add(BranchEntity("B01","YADOU"))
        branchList.add(BranchEntity("B02","Lesac"))
        branchList.add(BranchEntity("B03","ELLY"))
        branchList.add(BranchEntity("B04","FLORALPUNK"))
        branchList.add(BranchEntity("B05","Yuumy"))
        branchList.add(BranchEntity("B06","IELGY"))

        for (branch in branchList){
            branchDao.insertBranch(branch)
        }
    }

    override fun onItemDeleteClick(branch:BranchEntity){
        viewModel.deleteBranch(branch)
        Toast.makeText(this,"Delete successful", Toast.LENGTH_SHORT).show()
    }

    override fun onItemUpdateClick(branch:BranchEntity){
        var intent=Intent(this,EditBranch::class.java)
        val gson = Gson()
        val branchJson = gson.toJson(branch)
        intent.putExtra("branch",branchJson)
        startActivity(intent)
    }
}