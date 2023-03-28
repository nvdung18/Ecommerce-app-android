package com.example.ecommerce_app.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityForgetPassWordBinding
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.random.Random

class ForgetPassWordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPassWordBinding
    private var email: String = ""
    val uri = Uri.parse("content://com.example.admin/account")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPassWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendBtn.setOnClickListener {
            email = binding.emailEt.text.toString().trim()
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if(checkEmailExist(email)) {
                    sendPasswordResetEmail(email)
                } else {
                    Toast.makeText(this@ForgetPassWordActivity, "email is not exist", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backImg.setOnClickListener {
            onBackPressed()
        }
    }

    private fun checkEmailExist(email: String): Boolean {
        val cursor = contentResolver.query(uri, null, "email = ?", arrayOf(email), null) ?: null
        if(cursor != null) {
            return true
        }
        return false
    }

    private fun sendPasswordResetEmail(email: String) {
        val sharedPreferences = getSharedPreferences("Mypre", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        val numberRandom = getRandomNumber()

        val host = "smtp.gmail.com"
        val SenderEmail = "maihuymapkhungsw@gmail.com"
        val PasswordSenderEmail = "yivltildbcbrlgjy"
        val properties = System.getProperties()
        properties.put("mail.smtp.host", host)
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true")
        properties.put("mail.smtp.auth", "true")

        val session = Session.getInstance(properties, object : Authenticator(){
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(SenderEmail, PasswordSenderEmail)
            }
        })
        val mimeMessage = MimeMessage(session)
            mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email))
            mimeMessage.subject = "Reset your password"
            mimeMessage.setText("Hello you, reset code is ${numberRandom}")

            val thread = Thread {
                try {
                    Transport.send(mimeMessage)
                } catch (e: MessagingException) {
                    e.printStackTrace()
                }
            }
            thread.start()
            Toast.makeText(this@ForgetPassWordActivity, "Success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ForgetPassWordActivity, ResetPassWordActivity::class.java)
            intent.putExtra("numberRandom", numberRandom)
            intent.putExtra("email", email)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun getRandomNumber(): String {
        val min = 100000
        val max = 999999
        val random = Random.nextInt((max - min) + 1) + min
        return random.toString()
    }
}