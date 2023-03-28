package com.example.ecommerce_app.filter

import android.widget.Filter
import com.example.ecommerce_app.adapter.VisualResultAdapter
import com.example.ecommerce_app.models.BrandAndModel

class FilterProduct: Filter {
    private var filterList: ArrayList<BrandAndModel>
    private var adapterProduct: VisualResultAdapter

    constructor(filterList: ArrayList<BrandAndModel>, adapterProduct: VisualResultAdapter) {
        this.filterList = filterList
        this.adapterProduct = adapterProduct
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        if(constraint != null && constraint.isNotEmpty()) {
            constraint = constraint.toString().uppercase()
            val filteredModel: ArrayList<BrandAndModel> = ArrayList()
            for (i in 0 until filterList.size) {
                if(filterList[i].nameProduct.uppercase().contains(constraint)) {
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
        adapterProduct.listProduct = results?.values as ArrayList<BrandAndModel>
        adapterProduct.notifyDataSetChanged()
    }


}