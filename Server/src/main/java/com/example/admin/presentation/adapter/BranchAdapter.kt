package com.example.admin.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.databinding.SingleBranchBinding
import com.example.admin.presentation.activity.BranchActivity

class BranchAdapter(private val ctx: Context): RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {
    private val branchList:ArrayList<BranchEntity> = arrayListOf()
     class BranchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtIdBranch=itemView.findViewById<TextView>(R.id.txtIdBranch)
        val txtNameBranch=itemView.findViewById<TextView>(R.id.txtNameBranch)
        val btnEditBranch=itemView.findViewById<Button>(R.id.btnEditBranch)
        val btnDeleteBranch=itemView.findViewById<Button>(R.id.btnDeleteBranch)
    }

    private var listener: BranchItemClickAdapter? = null

    fun setListener(listener: BranchItemClickAdapter) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.single_branch,parent,false)
        return BranchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val branchItem:BranchEntity=branchList[position]
        holder.txtIdBranch.text=branchItem.idBranch
        holder.txtNameBranch.text=branchItem.nameBranch

        holder.btnDeleteBranch.setOnClickListener {
            deleteBranch(branchItem)
        }

        holder.btnEditBranch.setOnClickListener {
            listener?.onItemUpdateClick(branchItem)
        }

    }

    private fun deleteBranch(branch: BranchEntity) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to delete branch?")
        builder.setPositiveButton("Yes") { dialog, which ->
            listener?.onItemDeleteClick(branch)
        }
        builder.setNegativeButton("No") { dialog, which ->
            Log.e("a","false")
        }
        builder.show()
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

interface BranchItemClickAdapter{
    fun onItemDeleteClick(branch: BranchEntity)
    fun onItemUpdateClick(branch: BranchEntity) //edit

}