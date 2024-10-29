package com.example.bai63

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailNoteActivity : AppCompatActivity() {
    private lateinit var textViewTitle: TextView
    private lateinit var editTextContent: EditText
    private lateinit var databaseHelper: DatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_note)

        textViewTitle = findViewById(R.id.textViewTitle)
        editTextContent = findViewById(R.id.editTextContent)
        databaseHelper = DatabaseHelper(this)

        noteId = intent.getIntExtra("NOTE_ID", -1)
        val note = databaseHelper.getNoteById(noteId)

        textViewTitle.text = note?.title
        editTextContent.setText(note?.content)

        val buttonUpdate: Button = findViewById(R.id.buttonUpdate)
        buttonUpdate.setOnClickListener {
            val updatedContent = editTextContent.text.toString()
            databaseHelper.updateNoteContent(noteId, updatedContent)

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val buttonDelete: Button = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            databaseHelper.deleteNote(noteId)

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}