package com.example.ecommerce_app.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapter.ProductCheckOutAdapter
import com.example.ecommerce_app.databinding.ActivityCheckoutBinding
import com.example.ecommerce_app.models.CartDetailsAndProductAndBranch
import com.example.ecommerce_app.models.UserEntity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.text.DecimalFormat


class CheckoutActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding: ActivityCheckoutBinding
    lateinit var listProduct: ArrayList<CartDetailsAndProductAndBranch>
    lateinit var checkoutAdapter: ProductCheckOutAdapter
    val uri_account: Uri = Uri.parse("content://com.example.admin/account")
    val uri_user: Uri = Uri.parse("content://com.example.admin/user")
    val uri_Cart: Uri = Uri.parse("content://com.example.admin/Cart")
    val uri_CartDetails: Uri = Uri.parse("content://com.example.admin/CartDetails")
    val uri_checkout: Uri = Uri.parse("content://com.example.admin/Checkout")
    val uri_order: Uri = Uri.parse("content://com.example.admin/OrderTable")
    val uri_PromoCode: Uri = Uri.parse("content://com.example.admin/PromoCode")
    val uri_orderDetails: Uri = Uri.parse("content://com.example.admin/OrderDetails")

    private var addressCheckOut = ""
    private var nameCheckOut = ""
    private var phoneCheckOut = ""
    private var total: Double = 1.0
    private var voucher = ""
    private var method_payment = ""

    var orderNotes: String = ""
    var deliveryCharges: Double = 0.0
    var productMoney: Double = 0.0
    var idAccount: String = ""
    var idPayment: String = ""
    var idPromoCode: String = ""
    var idCheckout: String = ""
    var discountPercent: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("coverProduct")
        listProduct =
            bundle?.getSerializable("ARRAYLIST") as ArrayList<CartDetailsAndProductAndBranch>

        checkoutAdapter = ProductCheckOutAdapter(this@CheckoutActivity, listProduct)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = checkoutAdapter
        }

        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val address = result.data?.getStringExtra("address").toString()
                    if (address.isNotEmpty()) {
                        addressCheckOut = address
                        binding.notificationTv.visibility = View.VISIBLE
                    }
                }
            }

        val getContentName =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val address = result.data?.getStringExtra("address").toString()
                    if (address.isNotEmpty()) {
                        nameCheckOut = address
                        binding.nameTv.text = "Entered Name"
                        val textColor = ContextCompat.getColor(this, R.color.primary)
                        binding.nameTv.setTextColor(textColor);
                    }
                }
            }

        val getContentPhone =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val address = result.data?.getStringExtra("address").toString()
                    if (address.isNotEmpty()) {
                        phoneCheckOut = address
                        binding.phoneTv.text = "Entered Phone"
                        val textColor = ContextCompat.getColor(this, R.color.primary)
                        binding.phoneTv.setTextColor(textColor);
                    }
                }
            }

        binding.LltitleAddress.setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java)
            getContent.launch(intent)
        }

        binding.nameRecipient.setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java)
            getContentName.launch(intent)
        }

        binding.phoneRecipient.setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java)
            getContentPhone.launch(intent)
        }

        //Voucher
        binding.VoucherTv.setOnClickListener {
            val bottomSheetDialod = BottomSheetDialog(
                this, R.style.BottomSheetDialogTheme
            )

            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                com.example.ecommerce_app.R.layout.fragment_add_to_voucher,
                findViewById<ConstraintLayout>(com.example.ecommerce_app.R.id.bottomSheet)
            )

            bottomSheetView.findViewById<View>(R.id.saveVoucher_Btn).setOnClickListener {
                voucher = bottomSheetView.findViewById<EditText>(R.id.voucher_Et).text.toString()
                //check voucher not finish before dismiss sheetdialod
                bottomSheetDialod.dismiss()
            }

            bottomSheetDialod.setContentView(bottomSheetView)
            bottomSheetDialod.show()
        }
        //Method payment
        //price
        //format price to VND
        val vndFormat = DecimalFormat("#,### VND")
        val priceNew = checkoutAdapter.getPrice().toString().replace(",", ".")
        val priceNewNumber = priceNew.toDouble() // convert string to double
        val formattedAmount = vndFormat.format(priceNewNumber) // format double as VND

        binding.demoPrice.text = formattedAmount
        binding.priceOfficial.text = formattedAmount
        total = checkoutAdapter.getPrice()

        binding.orderBtn.setOnClickListener {
            if (listProduct.size != 0 && addressCheckOut.isNotEmpty() && nameCheckOut.isNotEmpty() && phoneCheckOut.isNotEmpty() && method_payment.isNotEmpty()) {
                addToCheckout()
            } else {
                Toast.makeText(this@CheckoutActivity, "Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToCheckout() {
        if(method_payment == "ship cod") {
            if (voucher == "promo_01" || voucher == "") {
                insertToCheckout()
                val idOrder = insertToOrder()
                insertToOrderDetails(idOrder)
                deleteCart()
            } else {
                if(checkVoucher() == true) {
                    insertToCheckout()
                    val idOrder = insertToOrder()
                    insertToOrderDetails(idOrder)
                    deleteCart()
                } else if(checkVoucher() == false) {
                    Toast.makeText(this, "Voucher Not Correct", Toast.LENGTH_SHORT).show()
                    insertToCheckout()
                    val idOrder = insertToOrder()
                    insertToOrderDetails(idOrder)
                    deleteCart()
                }
            }
            val intent = Intent(this@CheckoutActivity, HomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Success to order", Toast.LENGTH_SHORT).show()
        } else if(method_payment == "Razopay") {
            paymentRazopay()
        }
    }

    private fun paymentRazopay() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_zHf96fMn8W3lq2");

        val activity: Activity = this

        try {
            val options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
//            options.put("order_id", "order_DBJOWzybf0sJbb");
            var moneyTotal = 0.0
            if(discountPercent != 0f) {
                 moneyTotal = (total*discountPercent) / 100
            } else {
                moneyTotal = total
            }
            Log.e("Moneytotal", "$moneyTotal")
            options.put("amount", "${moneyTotal.toInt()}")//amountx100
            Log.d("AMOUNT", "${moneyTotal.toInt()}")
            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email", "gaurav.kumar@example.com")
            prefill.put("contact", "0774557680")
            options.put("prefill", prefill)
            checkout.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun checkVoucher(): Boolean {
        val cursor = contentResolver.query(uri_PromoCode, null, "idPromoCode = ?", arrayOf(voucher), null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                discountPercent = cursor.getFloat(cursor.getColumnIndexOrThrow("discountPercent")) ?: 0F
                idPromoCode = cursor.getString(cursor.getColumnIndexOrThrow("idPromoCode")) ?: "promo_01"
                return true
            }
            if (cursor != null) {
                cursor.close()
            }

        }
        return false
    }

    private fun insertToCheckout() {
        val user = getUserByToken()
        val values = ContentValues().apply {
            put("recipientName", nameCheckOut)
            put("recipientEmail", user!!.email)
            put("recipientPhoneNumber", phoneCheckOut)
            put("recipientAddress", addressCheckOut)
            if(discountPercent != 0f) {
                put("total", ((total*discountPercent)/100))
            } else {
                put("total", total)
            }
            put("namePayment", method_payment)
            put("idAccount", getCurrentIdAccount())
        }
        val uri_user_checkout = contentResolver.insert(uri_checkout, values)
    }

    private fun insertToOrder(): String {
        val values_order = ContentValues().apply {
            put("orderNotes", orderNotes)
            put("deliveryCharges", deliveryCharges)
            if(discountPercent != 0f) {
                put("total", ((total*discountPercent)/100))
                put("idPromocode", idPromoCode)
            } else {
                put("total", total)
                put("idPromocode", "promo_01")
            }
            put("idAccount", getCurrentIdAccount())
            put("namePayment", method_payment)
        }

        val uri_user_order = contentResolver.insert(uri_order, values_order)
        val lastPath = uri_user_order!!.lastPathSegment;
        Log.e("LastPath", lastPath.toString())
        val idOrder = "idOrder_${lastPath!!.toInt()}"
        return idOrder
    }

    private fun insertToOrderDetails(idOrder: String) {
        for (product in listProduct) {
            val values_ordersDetails = ContentValues().apply {
                put("idProduct", product.idProduct)
                put("idOrder", idOrder)
                put("total", product.price)
                put("quantity", product.quantity)
            }
            val uri_user_orderDetails =
                contentResolver.insert(uri_orderDetails, values_ordersDetails)
        }
    }

    private fun deleteCart() {
        val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        val cursor = contentResolver?.query(uri_account, null, "token = ?", arrayOf(token), null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val idAccount = cursor.getString(cursor.getColumnIndexOrThrow("idAccount"))
                Log.e("idAccount", "${idAccount}")

                val cursor =
                    contentResolver.query(uri_Cart, null, "idAccount = ?", arrayOf(idAccount), null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        val idCart = cursor.getString(cursor.getColumnIndexOrThrow("idCart"))
                        Log.e("Cartid", "${idCart}")
                        val cursor = contentResolver.delete(uri_CartDetails, "idCart = ?", arrayOf(idCart))
                    }
                }
            }
            if (cursor != null) {
                cursor.close()
            }

        }
    }

    private fun getCurrentIdAccount(): String? {
        val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        val cursor = contentResolver?.query(uri_account, null, "token = ?", arrayOf(token), null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val idAccount = cursor.getString(cursor.getColumnIndexOrThrow("idAccount"))
                return idAccount
            }
            if (cursor != null) {
                cursor.close()
            }

        }
        return null
    }

    private fun getUserByToken(): UserEntity? {
        val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        val cursor = contentResolver?.query(uri_user, null, "token = ?", arrayOf(token), null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val idUser = cursor.getString(cursor.getColumnIndexOrThrow("idUser"))
                val fullName = cursor.getString(cursor.getColumnIndexOrThrow("fullName"))
                val gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"))
                val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                val phoneNumber = cursor.getInt(cursor.getColumnIndexOrThrow("phoneNumber"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val role = cursor.getInt(cursor.getColumnIndexOrThrow("role"))
                val user = UserEntity(
                    idUser, fullName, gender, address, phoneNumber, email, role
                )
                return user
            }
            if (cursor != null) {
                cursor.close()
            }

        }
        return null
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_lock ->
                    if (checked) {
                        method_payment = "ship cod"
                    }
                R.id.radio_unlock ->
                    if (checked) {
                        method_payment = "Razopay"
                    }
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
       try {
           insertToCheckout()
           val idOrder = insertToOrder()
           insertToOrderDetails(idOrder)
           deleteCart()
           val intent = Intent(this@CheckoutActivity, HomeActivity::class.java)
           startActivity(intent)
           Toast.makeText(this, "Success to Order By Razopay", Toast.LENGTH_SHORT).show()
       } catch (e: Exception) {
           Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
       }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Faild to Order due to ${p1.toString()} ", Toast.LENGTH_SHORT).show()
    }
}