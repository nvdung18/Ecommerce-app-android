    package com.example.ecommerce_app.activity

    import android.content.ContentValues
    import android.content.Intent
    import android.net.Uri
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.Toast
    import com.example.ecommerce_app.R
    import com.example.ecommerce_app.databinding.ActivityResetPassWordBinding

    class ResetPassWordActivity : AppCompatActivity() {
        private lateinit var binding: ActivityResetPassWordBinding
        private var password: String = ""
        private var confirm_password: String = ""
        private var number: String = ""

        val uri = Uri.parse("content://com.example.admin/account")

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityResetPassWordBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val myNumber = intent.getStringExtra("numberRandom")
            val myEmail = intent.getStringExtra("email")

            binding.saveBtn.setOnClickListener {
                number = binding.numberEt.text.toString().trim()
                password = binding.passwordEt.text.toString().trim()
                confirm_password = binding.confirmPassEt.text.toString().trim()
                if(number.isEmpty()) {
                    Toast.makeText(this, "Number is empty", Toast.LENGTH_SHORT).show()
                } else if(number != myNumber) {
                    Toast.makeText(this, "Number is not be similiar to code", Toast.LENGTH_SHORT).show()
                } else if(password.isEmpty()) {
                    Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show()
                } else if(confirm_password.isEmpty()){
                    Toast.makeText(this, "Confirm password is empty", Toast.LENGTH_SHORT).show()
                } else if(confirm_password != password) {
                    Toast.makeText(this, "Confirmpassword is not be similar to Password", Toast.LENGTH_SHORT).show()
                } else {
                    updatePassword(myEmail, password, confirm_password)
                    resetUI()
                }
            }

            binding.backImg.setOnClickListener {
                onBackPressed()
            }

            binding.signIpTv.setOnClickListener {
                val intent = Intent(this@ResetPassWordActivity, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }

        private fun resetUI() {
            binding.numberEt.setText("")
            binding.passwordEt.setText("")
            binding.confirmPassEt.setText("")
        }

        private fun updatePassword(myEmail: String?, password: String?, confirm_password: String?) {
            val cursor = contentResolver.query(uri, null, "email = ?", arrayOf(myEmail), null)
            if(cursor != null && cursor.moveToFirst()) {
                val values = ContentValues().apply {
                    put("password", password)
                    put("idUser", cursor.getString(cursor.getColumnIndexOrThrow("idUser")))
                }
                contentResolver.update(uri, values,"email = ?", arrayOf(myEmail))
                Toast.makeText(this@ResetPassWordActivity, "Success", Toast.LENGTH_SHORT).show()
            } else if(cursor == null) {
                Toast.makeText(this@ResetPassWordActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }