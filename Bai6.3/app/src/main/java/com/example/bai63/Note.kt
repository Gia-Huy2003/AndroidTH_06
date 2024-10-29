package com.example.bai63

data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val date: String = System.currentTimeMillis().toString()
)