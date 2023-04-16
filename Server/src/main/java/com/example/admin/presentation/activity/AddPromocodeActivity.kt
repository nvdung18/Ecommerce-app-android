package com.example.admin.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.data.room.product.ProductEntity
import com.example.admin.data.room.promocode.PromocodeEntity
import com.example.admin.data.room.promocode.PromocodeViewModel
import com.example.admin.databinding.ActivityAddPromocodeBinding
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class AddPromocodeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddPromocodeBinding
    private lateinit var promocodeViewModel: PromocodeViewModel
    private lateinit var promocodeItem:PromocodeEntity
    private var funActivity:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_promocode)

        binding= ActivityAddPromocodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        promocodeViewModel= ViewModelProviders.of(this).get(PromocodeViewModel::class.java)

        initComponents()
    }

    private fun initComponents() {
        var i=intent
        funActivity=i.getStringExtra("funActivity").toString()

        if(funActivity=="Edit"){
            binding.btnAddPromocode.setText("Update")
            binding.txtTitleAddPromocode.setText("Update promocode")

            setInforUpdatePromocode()
        }
        binding.btnAddPromocode.setOnClickListener {
            if(funActivity=="Edit"){
                updatePromocode()
            }else{
                addPromocode()
            }
        }

        binding.imgBtnAddPromocodeBack.setOnClickListener {
            back()
        }
    }

    private fun setInforUpdatePromocode() {
        var def = DecimalFormat("#,###.###")//use to format number like this: 100.000
        def.decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }

        var i=intent
        var promocodeJson=i.getStringExtra("promocode")

        var gson= Gson() //convert json to object
        val type = object : TypeToken<PromocodeEntity>() {}.type
        promocodeItem = gson.fromJson(promocodeJson, type)

        binding.edtIdPromocode.isEnabled=false //we can't change id Promocode, because it id of entity => we can't change
        binding.edtIdPromocode.setText(promocodeItem.idPromoCode)
        binding.edtDiscountPromocode.setText(def.format(promocodeItem.discountPercent).toString())
        binding.edtDescriptionPromocode.setText((promocodeItem.description))
    }

    private fun updatePromocode() {
        var idPromocode=binding.edtIdPromocode.text.toString()
        var discountPercent=binding.edtDiscountPromocode.text.toString().toFloat()
        var description=binding.edtDescriptionPromocode.text.toString()

        var promocodeEntity=PromocodeEntity(idPromocode,description, discountPercent)

        Log.e("a",promocodeEntity.toString())

        promocodeViewModel.updatePromocode(promocodeEntity)

        Toast.makeText(this,"Update Promocode Successful", Toast.LENGTH_SHORT).show()

        val intent= Intent(this, PromoCodeActivity::class.java)
        startActivity(intent)
    }

    private fun addPromocode() {
        var idPromocode=binding.edtIdPromocode.text.toString()
        var discountPercent=binding.edtDiscountPromocode.text.toString().toFloat()
        var description=binding.edtDescriptionPromocode.text.toString()

        var promocodeEntity=PromocodeEntity(idPromocode,description, discountPercent)

        promocodeViewModel.insertPromocode(promocodeEntity)

        Toast.makeText(this,"Add Promocode Successful", Toast.LENGTH_SHORT).show()

        val intent= Intent(this, PromoCodeActivity::class.java)
        startActivity(intent)
    }

    private fun back() {
        var intent=Intent(this,PromoCodeActivity::class.java)
        startActivity(intent)
    }
}