package com.example.ecommerce_app.filter

import android.widget.Filter
import com.example.ecommerce_app.adapter.CategoryAdapter
import com.example.ecommerce_app.models.BranchEntity

class FilterCategory: Filter {

    private var filterList: ArrayList<BranchEntity>

    private var adapterCategory: CategoryAdapter

    constructor(filterList: ArrayList<BranchEntity>, adapterCategory: CategoryAdapter): super() {
        this.filterList = filterList
        this.adapterCategory = adapterCategory
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        if(constraint != null && constraint.isNotEmpty()) {
            constraint = constraint.toString().uppercase()
            val filteredModel: ArrayList<BranchEntity> = ArrayList()
            for(i in 0 until filterList.size) {
                if(filterList[i].nameBranch.uppercase().contains(constraint)) {
                    filteredModel.add(filterList[i])
                }
            }

            results.count = filteredModel.size
            results.values = filteredModel
        } else {
            results.count = filterList.size
            results.values = filterList
        }
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapterCategory.listCategory = results?.values as ArrayList<BranchEntity>
        adapterCategory.notifyDataSetChanged()
    }

}