package com.example.bai63

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        // Xử lý sự kiện cho button thêm ghi chú
        val buttonAddNote: Button = findViewById(R.id.buttonAddNote)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        // Khởi tạo RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadNotes(recyclerView)
    }

    private fun loadNotes(recyclerView: RecyclerView) {
        val notes = databaseHelper.getAllNotes()
        noteAdapter = NoteAdapter(notes) { note ->
            val intent = Intent(this, DetailNoteActivity::class.java)
            intent.putExtra("NOTE_ID", note.id)
            startActivity(intent)
        }
        recyclerView.adapter = noteAdapter
    }

    override fun onResume() {
        super.onResume()
        loadNotes(findViewById(R.id.recyclerView))
    }
}
