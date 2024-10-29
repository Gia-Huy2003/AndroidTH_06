package com.example.bai62

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    companion object {
        private const val DATABASE_NAME = "contacts.db"
        private const val TABLE_CONTACTS = "Contacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_CONTACTS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_PHONE TEXT, " +
                "$COLUMN_EMAIL TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addContact(contact: Contact) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_PHONE, contact.phone)
            put(COLUMN_EMAIL, contact.email)
        }
        db.insert(TABLE_CONTACTS, null, values)
        db.close()
    }

    fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_CONTACTS", null)

        if (cursor.moveToFirst()) {
            do {
                val contact = Contact(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    phone = cursor.getString(2),
                    email = cursor.getString(3)
                )
                contacts.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contacts
    }

    fun getContactById(id: Int): Contact? {
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_CONTACTS, null, "$COLUMN_ID=?", arrayOf(id.toString()), null, null, null)

        return if (cursor != null && cursor.moveToFirst()) {
            val contact = Contact(
                id = cursor.getInt(0),
                name = cursor.getString(1),
                phone = cursor.getString(2),
                email = cursor.getString(3)
            )
            cursor.close()
            contact
        } else {
            null
        }
    }
}