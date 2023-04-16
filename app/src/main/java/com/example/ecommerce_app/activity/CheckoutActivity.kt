package com.example.ecommerce_app.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapter.ProductCheckOutAdapter
import com.example.ecommerce_app.databinding.ActivityCheckoutBinding
import com.example.ecommerce_app.models.BrandAndModel
import com.example.ecommerce_app.models.CartDetailsAndProduct
import com.example.ecommerce_app.models.CartDetailsAndProductAndBranch
import com.google.android.material.bottomsheet.BottomSheetDialog

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    lateinit var listProduct: ArrayList<CartDetailsAndProductAndBranch>
    lateinit var checkoutAdapter: ProductCheckOutAdapter
    val uri_account: Uri = Uri.parse("content://com.example.admin/account")

    private var addressCheckOut = ""
    private var total: Double = 1.0
    private var voucher = ""
    private var method_payment = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("coverProduct")
        listProduct = bundle?.getSerializable("ARRAYLIST") as ArrayList<CartDetailsAndProductAndBranch>

        checkoutAdapter = ProductCheckOutAdapter(this@CheckoutActivity, listProduct)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = checkoutAdapter
        }

        //Address

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                val address = result.data?.getStringExtra("address").toString()
                if(address.isNotEmpty()) {
                    addressCheckOut = address
                }
            }
        }

        binding.imgAddress.setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java)
            getContent.launch(intent)
        }
        //Voucher

        binding.VoucherTv.setOnClickListener {
            val bottomSheetDialod = BottomSheetDialog(
                this, R.style.BottomSheetDialogTheme
            )

            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.fragment_add_to_voucher,
                findViewById<ConstraintLayout>(R.id.bottomSheet)
            )

            bottomSheetView.findViewById<View>(R.id.saveBtn).setOnClickListener {
                voucher = bottomSheetView.findViewById<EditText>(R.id.voucher_Et).text.toString()
                //check voucher not finish before dismiss sheetdialod
                bottomSheetDialod.dismiss()
            }

            bottomSheetDialod.setContentView(bottomSheetView)
            bottomSheetDialod.show()
        }
        //Method payment
        val getMethodPayment = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                //get id payment
                method_payment = result.data?.getStringExtra("idpayment").toString()
                if(method_payment != "") {
                    binding.tvPaymentMethod.text = "Thanh Toan Bang Momo"
                }
            }
        }

        binding.tvPaymentMethod.setOnClickListener {
            val intent = Intent(this@CheckoutActivity, PaymentMethodActivity::class.java)
            getMethodPayment.launch(intent)
        }
         //price
        binding.demoPrice.text = checkoutAdapter.getPrice().toString()
        binding.priceOfficial.text = checkoutAdapter.getPrice().toString()
        total = checkoutAdapter.getPrice()

        binding.orderBtn.setOnClickListener {
            if(listProduct.size != 0) {
                addToCheckout()
            } else {
                Toast.makeText(this@CheckoutActivity, "Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToCheckout() {
         val values = ContentValues().apply {
             put("recipientAddress", addressCheckOut)
             put("total", total)
             put("idAccount", getCurrentIdAccount())

         }
    }

    private fun getCurrentIdAccount(): String? {
        val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        val cursor = contentResolver?.query(uri_account, null, "token = ?", arrayOf(token), null)
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                val idAccount = cursor.getString(cursor.getColumnIndexOrThrow("idAccount"))
                return idAccount
            }
            if(cursor != null) {
                cursor.close()
            }

        }
        return null
    }
}