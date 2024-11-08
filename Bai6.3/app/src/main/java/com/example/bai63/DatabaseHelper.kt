package com.example.bai63

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    companion object {
        private const val DATABASE_NAME = "notes.db"
        private const val TABLE_NOTES = "Notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NOTES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_CONTENT TEXT, " +
                "$COLUMN_DATE TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }

    fun addNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_DATE, System.currentTimeMillis().toString())
        }
        db.insert(TABLE_NOTES, null, values)
        db.close()
    }

    fun getAllNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NOTES", null)

        if (cursor.moveToFirst()) {
            do {
                val note = Note(
                    id = cursor.getInt(0),
                    title = cursor.getString(1),
                    content = cursor.getString(2),
                    date = cursor.getString(3)
                )
                notes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return notes
    }

    fun getNoteById(id: Int): Note? {
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NOTES, null, "$COLUMN_ID=?", arrayOf(id.toString()), null, null, null)

        return if (cursor.moveToFirst()) {
            val note = Note(
                id = cursor.getInt(0),
                title = cursor.getString(1),
                content = cursor.getString(2),
                date = cursor.getString(3)
            )
            cursor.close()
            note
        } else {
            cursor.close()
            null
        }
    }

    fun updateNoteContent(id: Int, content: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CONTENT, content)
        }
        db.update(TABLE_NOTES, values, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteNote(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NOTES, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
    }
}