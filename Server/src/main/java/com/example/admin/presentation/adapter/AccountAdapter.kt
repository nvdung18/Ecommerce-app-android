package com.example.admin.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.data.room.account.AccountEntity
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.branch.BranchViewModel

class AccountAdapter(private val ctx: Context): RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {
    private val accountList:ArrayList<AccountEntity> = arrayListOf()

    inner class AccountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtIdUser=itemView.findViewById<TextView>(R.id.txtIdUser)
        val txtIdAccount=itemView.findViewById<TextView>(R.id.txtIdAccount)
        val txtUserName=itemView.findViewById<TextView>(R.id.txtUserName)
        val btnDetailsAccount=itemView.findViewById<Button>(R.id.btnDetailsAccount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.single_account,parent,false)
        return AccountViewHolder(view)
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val accountItem=accountList[position]
        holder.txtIdAccount.text="Id user: ${accountItem.idAccount}"
        holder.txtIdUser.text="Id account: ${accountItem.idUser}"
        holder.txtUserName.text="User name: ${accountItem.userName}"
    }
    fun updateList(newList: List<AccountEntity>){
        accountList.clear()
        accountList.addAll(newList)
        notifyDataSetChanged()
    }

}