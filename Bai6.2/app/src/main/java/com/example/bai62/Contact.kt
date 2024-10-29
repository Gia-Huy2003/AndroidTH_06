package com.example.bai62

data class Contact(
    val id: Int = 0,
    val name: String,
    val phone: String,
    val email: String
) {
    override fun toString(): String {
        return name // Hiển thị tên trong ListView
    }
}