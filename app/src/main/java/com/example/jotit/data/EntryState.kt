package com.example.jotit.data

import java.util.Date

data class EntryState(
    val entries: List<Entry> = emptyList(),
    val date: Date = Date(),// setting default value to current date
    val gratitude: String = "",
    val todayGreat: String = "",
    val amazingThings: String = "",
    val betterThings: String = "",
    val isAddingEntry: Boolean = false,
    val sortType: SortType = SortType.DATE // by default
)
