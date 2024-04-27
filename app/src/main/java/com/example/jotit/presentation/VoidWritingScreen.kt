package com.example.jotit.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun VoidWritingScreen() {
    var text by remember { mutableStateOf("") }
    val textEntries = remember { mutableStateListOf<String>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth().offset(y = (-20).dp)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                reverseLayout = true,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(textEntries) { index, entry ->
                    Text(
                        text = entry,
                        color = Color.Black.copy(alpha = calculateOpacity(index)),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
            // Text input field with placeholder
            BasicTextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                    if (newText.endsWith("\n")) { // When enter is pressed, submit the line
                        textEntries.add(0, text.dropLast(1).trim()) // Add new text at the beginning, remove the newline character
                        text = ""
                        if (textEntries.size > 2) textEntries.removeAt(2) // Keep only the latest two entries
                    }
                },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text("Empty your mind...", color = Color.Gray, fontSize = 16.sp)
                    }
                    innerTextField()
                }
            )
        }
    }
}

fun calculateOpacity(index: Int): Float {
    return when (index) {
        0 -> 0.5f  // Most recent line at 70% opacity
        1 -> 0.3f  // Next line at 30% opacity
        else -> 0f // Older lines are invisible (0% opacity)
    }
}