package com.example.jotit

import androidx.room.Database

@Database(
    entities = [Entry::class],
    version = 1
)
abstract class EntryDatabase {
    abstract val dao: EntryDao
}