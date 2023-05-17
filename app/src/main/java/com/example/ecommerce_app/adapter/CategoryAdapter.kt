package com.example.ecommerce_app.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ecommerce_app.Utils
import com.example.ecommerce_app.activity.AllProductByBranchActivity
import com.example.ecommerce_app.databinding.CategorySingleBinding
import com.example.ecommerce_app.filter.FilterCategory
import com.example.ecommerce_app.models.BranchEntity

class CategoryAdapter: Adapter<CategoryAdapter.HolderAdapter> , Filterable {

    private lateinit var binding: CategorySingleBinding

     lateinit var listCategory: ArrayList<BranchEntity>
    private lateinit var context: Context
    private var filter: FilterCategory? = null
    private lateinit var filterList:  ArrayList<BranchEntity>

    constructor(listCategory: ArrayList<BranchEntity>, context: Context) {
        this.listCategory = listCategory
        this.context = context
        this.filterList = listCategory
    }

    inner class HolderAdapter(itemView: View): ViewHolder(itemView) {
        val categoryName_CateSingle = binding.categoryNameCateSingle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderAdapter {
        binding = CategorySingleBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderAdapter(binding.root)
    }

    override fun onBindViewHolder(holder: HolderAdapter, position: Int) {
        val model = listCategory[position]
        holder.categoryName_CateSingle.text = model.nameBranch

        holder.itemView.setOnClickListener {
            val idBranch = model.idBranch
            val nameBranch = model.nameBranch
            val intent = Intent(context, AllProductByBranchActivity::class.java)
            intent.putExtra("idBranch", idBranch)
            intent.putExtra("nameBranch", nameBranch)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return listCategory.size
    }

    override fun getFilter(): Filter {
        if(filter == null) {
            filter = FilterCategory(filterList, this)
        }
        return filter as FilterCategory
    }
}