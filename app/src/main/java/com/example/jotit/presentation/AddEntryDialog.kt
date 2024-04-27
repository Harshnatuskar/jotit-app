@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jotit.presentation

import android.media.metrics.Event
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jotit.data.EntryEvent
import com.example.jotit.data.EntryState
import com.example.jotit.ui.theme.GeneralSans

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun AddEntryDialog(
    state: EntryState,
    onEvent: (EntryEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
            onEvent(EntryEvent.HideDialog)
        },
        title = {
            Text(text = "Add Entry",
                fontFamily = GeneralSans,
                fontWeight = FontWeight.SemiBold)
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
                        Text(text = "Grateful for:" ,
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.Normal
                        )
                    }
                )
                TextField(
                    value = state.todayGreat,
                    onValueChange = {
                        onEvent(EntryEvent.SetTodayGreat(it))
                    },
                    placeholder = {
                        Text(text = "What would make today great?",
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.Normal)
                    }
                )
                TextField(
                    value = state.amazingThings,
                    onValueChange = {
                        onEvent(EntryEvent.SetAmazingThings(it))
                    },
                    placeholder = {
                        Text(text = "Amazing things that happened today:",
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.Normal)
                    }
                )
                TextField(
                    value = state.betterThings,
                    onValueChange = {
                        onEvent(EntryEvent.SetBetterThings(it))
                    },
                    placeholder = {
                        Text(text = "Things that I would do better:",
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.Normal)
                    }
                )
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                onClick = {
                    onEvent(EntryEvent.SaveEvent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray, // Set the button background color to gray
                ),
                shape = RoundedCornerShape(12.dp) // Set the corner radius to 12dp
            ) {
                Text(
                    text = "Save",
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
    )
}