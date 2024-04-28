package com.example.jotit.presentation

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jotit.R
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
                    color = Color.DarkGray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    modifier = Modifier.padding(bottom = 16.dp),
                    onClick = {
                        navController.navigate("void_screen")
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.sparks),
                        contentDescription = "Void Writing"
                    )
                }
                FloatingActionButton(onClick = {
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
                        .height(58.dp)
                        .border(
                            BorderStroke(0.01.dp, Color.LightGray), // Adjusted border color to be lighter
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 14.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "${entry.entryDate}",
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.Normal,
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
}
