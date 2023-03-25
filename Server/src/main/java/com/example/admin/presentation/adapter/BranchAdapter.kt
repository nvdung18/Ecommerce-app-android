package com.example.admin.presentation.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.data.model.Branch
import com.example.admin.data.model.Product
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.databinding.ActivityBranchBinding
import com.example.admin.databinding.SingleBranchBinding

class BranchAdapter (private val ctx: Context): RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {
    private val branchList:ArrayList<BranchEntity> = arrayListOf()
     class BranchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtIdBranch=itemView.findViewById<TextView>(R.id.txtIdBranch)
        val txtNameBranch=itemView.findViewById<TextView>(R.id.txtNameBranch)
        val btnEditBranch=itemView.findViewById<Button>(R.id.btnEditBranch)
        val btnDeleteBranch=itemView.findViewById<Button>(R.id.btnDeleteBranch)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.single_branch,parent,false)
        return BranchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val branchItem:BranchEntity=branchList[position]
        holder.txtIdBranch.text=branchItem.idBranch
        holder.txtNameBranch.text=branchItem.nameBranch


    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    fun updateList(newList: List<BranchEntity>){
        branchList.clear()
        branchList.addAll(newList)
        notifyDataSetChanged()
    }

}