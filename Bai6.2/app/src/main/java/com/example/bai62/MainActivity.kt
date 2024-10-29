package com.example.bai62

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listViewContacts: ListView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewContacts = findViewById(R.id.listViewContacts)
        databaseHelper = DatabaseHelper(this)
        loadContacts()

        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }

        listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val selectedContact = listViewContacts.getItemAtPosition(position) as Contact
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("CONTACT_ID", selectedContact.id)
            startActivity(intent)
        }
    }

    private fun loadContacts() {
        val contacts = databaseHelper.getAllContacts()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts)
        listViewContacts.adapter = adapter
    }
}