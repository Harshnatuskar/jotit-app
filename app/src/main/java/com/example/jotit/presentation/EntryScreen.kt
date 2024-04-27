package com.example.jotit.presentation

import android.media.metrics.Event
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jotit.data.Entry
import com.example.jotit.data.EntryEvent
import com.example.jotit.data.EntryState
import com.example.jotit.data.SortType

@Composable
fun EntryScreen(
    state: EntryState,
    onEvent: (EntryEvent) -> Unit
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(EntryEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Add Entry"
                )
            }
        }
    ) { padding->
        if(state.isAddingEntry) {
            AddEntryDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item{
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SortType.entries.forEach {
                        sortType ->
                            Row(
                                modifier= Modifier
                                    .clickable {
                                        onEvent(EntryEvent.SortEntry(sortType))
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                    //add sorts in future if required
                            }
                    }
                }
            }
            items(state.entries){  entry ->
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${entry.entryDate}",
                            fontSize = 18.sp
                        )
                        Text(
                            text = entry.gratitude,
                            fontSize = 12.sp
                        )
                        IconButton(onClick = {
                            onEvent(EntryEvent.DeleteEntry(entry))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Contact")
                        }
                    }
                }
            }
        }
    }
}