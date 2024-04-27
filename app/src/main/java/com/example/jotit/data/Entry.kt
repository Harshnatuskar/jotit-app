package com.example.jotit.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val entryDate: String = getCurrentDate(),
    val gratitude: String,
    val todayGreat: String,
    val amazingThings: String,
    val betterThings: String
){
    companion object {
        // Function to get the current date in the desired format
        private fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val currentDate = Date()
            return dateFormat.format(currentDate)
        }
    }
}
