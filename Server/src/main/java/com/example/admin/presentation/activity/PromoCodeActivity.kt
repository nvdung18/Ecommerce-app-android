package com.example.admin.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.data.room.promocode.PromocodeEntity
import com.example.admin.data.room.promocode.PromocodeViewModel
import com.example.admin.databinding.ActivityPromoCodeBinding
import com.example.admin.presentation.adapter.PromocodeAdapter
import com.example.admin.presentation.adapter.PromocodeItemClickAdapter
import com.google.gson.Gson

class PromoCodeActivity : AppCompatActivity(),PromocodeItemClickAdapter {
    private lateinit var adapter: PromocodeAdapter
    private lateinit var binding:ActivityPromoCodeBinding
    private lateinit var promocodeViewModel: PromocodeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_code)

        binding= ActivityPromoCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        promocodeViewModel=ViewModelProviders.of(this).get(PromocodeViewModel::class.java)
        promocodeViewModel.allPromocode.observe(this,{List->
            List?.let {
                adapter.updateList(it)
            }
        })

        initComponents()
    }

    private fun initComponents() {
        adapter= PromocodeAdapter(this)
        adapter.setListener(this)
        binding.rvAllPromocode.adapter=adapter
        binding.rvAllPromocode.layoutManager= LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.btnAddPromocde.setOnClickListener{
            addProductActivity()
        }

        binding.imageBtnBackPromocode.setOnClickListener {
            back()
        }

    }

    private fun back() {
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun addProductActivity() {
        var intent=Intent(this,AddPromocodeActivity::class.java)
        startActivity(intent)
    }

    override fun onItemDeleteClick(promocode: PromocodeEntity) {
        promocodeViewModel.deletePromocode(promocode)
        Toast.makeText(this,"Delete successful", Toast.LENGTH_SHORT).show()
    }

    override fun onItemUpdateClick(promocode: PromocodeEntity) {
        var intent=Intent(this,AddPromocodeActivity::class.java)
        val gson = Gson()
        val productJson = gson.toJson(promocode)
        intent.putExtra("promocode",productJson)
        intent.putExtra("funActivity","Edit")
        startActivity(intent)
    }
}