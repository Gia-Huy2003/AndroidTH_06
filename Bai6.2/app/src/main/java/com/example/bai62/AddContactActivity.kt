package com.example.bai62

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddContactActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        editTextName = findViewById(R.id.editTextName)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextEmail = findViewById(R.id.editTextEmail)
        databaseHelper = DatabaseHelper(this)

        val buttonSave: Button = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            val email = editTextEmail.text.toString()

            val newContact = Contact(name = name, phone = phone, email = email)
            databaseHelper.addContact(newContact)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}