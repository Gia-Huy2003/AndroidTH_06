package com.example.bai63

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddNoteActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextContent = findViewById(R.id.editTextContent)
        databaseHelper = DatabaseHelper(this)

        val buttonSave: Button = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            val title = editTextTitle.text.toString()
            val content = editTextContent.text.toString()
            val newNote = Note(title = title, content = content)
            databaseHelper.addNote(newNote)

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}