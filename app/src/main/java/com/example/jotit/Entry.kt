package com.example.jotit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val entryDate: String,
    val gratitude: String,
    val greatToday: String,
    val amazingThings: String,
    val betterThings: String
)
