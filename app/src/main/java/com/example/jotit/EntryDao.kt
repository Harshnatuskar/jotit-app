package com.example.jotit

import android.provider.ContactsContract.CommonDataKinds.Contactables
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    @Upsert //update and insert in same fun
    suspend fun upsertEntry(entry: Entry) // suspend makes them run in coroutine

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("SELECT * FROM entry ORDER BY entryDate ASC")
    fun getEntriesOrderedByDate(): Flow<List<Entry>>
}