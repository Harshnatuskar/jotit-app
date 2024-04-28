package com.example.jotit.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jotit.R
import com.example.jotit.data.Entry
import com.example.jotit.data.EntryEvent
import com.example.jotit.data.EntryState
import com.example.jotit.data.SortType
import com.example.jotit.ui.theme.GeneralSans


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(
    state: EntryState,
    onEvent: (EntryEvent) -> Unit,
    navController: NavHostController
) {
    var selectedEntry: Entry? by remember { mutableStateOf(null) }
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Jotit",
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Divider(
                    color = Color.Gray,
                    thickness = 1.4.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .border(
                            BorderStroke(0.5.dp, Color.LightGray),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .size(56.dp),
                    containerColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    onClick = {
                        navController.navigate("void_screen")
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.sparks),
                        contentDescription = "Void Writing"
                    )
                }
                FloatingActionButton(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .border(
                            BorderStroke(0.5.dp, Color.LightGray),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .size(56.dp),
                    containerColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    onClick = {
                    onEvent(EntryEvent.ShowDialog)
                }) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Add Entry"
                    )
                }
            }
        }
    ) { padding ->
        if (state.isAddingEntry) {
            AddEntryDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 14.dp, end = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.entries.forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(EntryEvent.SortEntry(sortType))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Add sorts in the future if required
                        }
                    }
                }
            }
            items(state.entries) { entry ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(66.dp)
                        .border(
                            BorderStroke(
                                0.5.dp,
                                Color.LightGray
                            ), // Adjusted border color to be lighter
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clickable {
                            // Store the selected entry
                            selectedEntry = entry
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 14.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "${entry.entryDate}",
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                        Text(
                            text = entry.gratitude,
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                    IconButton(
                        onClick = {
                            onEvent(EntryEvent.DeleteEntry(entry))
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.cross),
                            contentDescription = "Delete",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }

    selectedEntry?.let { entry ->
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog
                selectedEntry = null
            },
            title = {
                Text(text = "Entry Details",
                    fontWeight = FontWeight.Medium,
                )
            },
            text = {
                // Display entry details in the dialog
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal)) {
                            append("Date: ")
                        }
                        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Light)) {
                            append("${entry.entryDate}\n")
                        }
                        append("\n")
                        withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal)) {
                            append("Amazing Things that happened day before: ")
                        }
                        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Light)) {
                            append("${entry.amazingThings}\n")
                        }
                        append("\n")
                        withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal)) {
                            append("Could have done better: ")
                        }
                        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Light)) {
                            append("${entry.betterThings}\n")
                        }
                        append("\n")
                        withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal)) {
                            append("Grateful for: ")
                        }
                        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Light)) {
                            append("${entry.gratitude}\n")
                        }
                        append("\n")
                        withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal)) {
                            append("What would make the day great? ")
                        }
                        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Light)) {
                            append("${entry.todayGreat}\n")
                        }
                    },
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Dismiss the dialog
                        selectedEntry = null
                    },
                            colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray, // Set the button background color to gray
                    ),
                shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Close")
                }
            }
        )
    }
}
