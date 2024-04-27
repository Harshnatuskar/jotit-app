package com.example.jotit.data
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Entry::class],
    version = 1
)
abstract class EntryDatabase:RoomDatabase() {
    abstract val dao: EntryDao
}