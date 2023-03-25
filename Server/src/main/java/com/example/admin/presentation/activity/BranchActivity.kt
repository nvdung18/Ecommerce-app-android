package com.example.admin.presentation.activity

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.branch.BranchViewModel
import com.example.admin.databinding.ActivityBranchBinding
import com.example.admin.presentation.adapter.BranchAdapter

class BranchActivity : AppCompatActivity() {
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
//        var instance= AppDatabase.getInstance(this)
//        val allBranch:LiveData<List<BranchEntity>> = instance.branchDao().getAllBranch()
//        Log.e("a",allBranch.toString())
//        branchDao.insertBranch(BranchEntity("b01","jfdf"))
//        branchDao.insertBranch(BranchEntity("b02","edfsdf"))
//        branchDao.insertBranch(BranchEntity("b03","fasdf"))
//        branchDao.insertBranch(BranchEntity("b04","edfsdf"))
//        branchDao.insertBranch(BranchEntity("b05","fasdf"))
//        binding.branchViewModel=viewModel

        viewModel=ViewModelProviders.of(this).get(BranchViewModel::class.java)

        viewModel.allBranch.observe(this,{List->
            List?.let {
                adapter.updateList(it)
            }
        })

//        binding.btnAddBranch.setOnClickListener {
//            viewModel.changeDataList()
//
//            val adapter= BranchAdapter(viewModel.listBranch)
//            Log.e("a",viewModel.listBranch[2].toString())
//            binding.rvAllBranch.adapter=adapter
//            binding.rvAllBranch.layoutManager= LinearLayoutManager(
//                this,
//                LinearLayoutManager.VERTICAL,
//                false
//            )
//        }
    }

    private fun initComponents() {
        adapter= BranchAdapter(this)
        binding.rvAllBranch.adapter=adapter
        binding.rvAllBranch.layoutManager= LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.btnAddBranch.setOnClickListener {
            addBranchActivity()
        }
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
}