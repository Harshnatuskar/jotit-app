@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jotit.presentation

import android.media.metrics.Event
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jotit.data.EntryEvent
import com.example.jotit.data.EntryState

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun AddEntryDialog(
    state: EntryState,
    onEvent: (EntryEvent) -> Event,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
            onEvent(EntryEvent.HideDialog)
        },
        title = {
            Text(text = "Add Entry")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.gratitude,
                    onValueChange = {
                        onEvent(EntryEvent.SetGratitude(it))
                    },
                    placeholder = {
                        Text(text = "Grateful for:")
                    }
                )
                TextField(
                    value = state.todayGreat,
                    onValueChange = {
                        onEvent(EntryEvent.SetTodayGreat(it))
                    },
                    placeholder = {
                        Text(text = "today great:")
                    }
                )
                TextField(
                    value = state.amazingThings,
                    onValueChange = {
                        onEvent(EntryEvent.SetAmazingThings(it))
                    },
                    placeholder = {
                        Text(text = "Amazing things that happened today:")
                    }
                )
                TextField(
                    value = state.betterThings,
                    onValueChange = {
                        onEvent(EntryEvent.SetBetterThings(it))
                    },
                    placeholder = {
                        Text(text = "Things that I would do better:")
                    }
                )
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(EntryEvent.SaveEvent)
                }
            ) {
                Text(text = "Save")
            }
        },
    )
}