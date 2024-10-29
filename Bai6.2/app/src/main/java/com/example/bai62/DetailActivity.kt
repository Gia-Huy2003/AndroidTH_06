package com.example.bai62

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var textViewName: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var databaseHelper: DatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        textViewName = findViewById(R.id.textViewName)
        textViewPhone = findViewById(R.id.textViewPhone)
        textViewEmail = findViewById(R.id.textViewEmail)
        databaseHelper = DatabaseHelper(this)

        contactId = intent.getIntExtra("CONTACT_ID", -1)
        val contact = databaseHelper.getContactById(contactId)

        textViewName.text = contact?.name
        textViewPhone.text = contact?.phone
        textViewEmail.text = contact?.email

        val callButton: Button = findViewById(R.id.callButton)
        callButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${contact?.phone}")
            startActivity(callIntent)
        }

        val emailButton: Button = findViewById(R.id.emailButton)
        emailButton.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:${contact?.email}")
            startActivity(Intent.createChooser(emailIntent, "Gửi email..."))
        }
    }
}