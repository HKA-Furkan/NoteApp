package com.example.noteapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun NoteDetailScreen(note: Note) {
    Column() {
        val titleState = remember { mutableStateOf(TextFieldValue(note.title))}
        val contentState = remember { mutableStateOf(TextFieldValue(note.content))}

        TextField(
            value = titleState.value,
            onValueChange = {
                titleState.value = it
                note.title = titleState.value.text
            },
            modifier = Modifier.fillMaxWidth())


        TextField(
            value = contentState.value,
            onValueChange = {
                contentState.value = it
                note.content = contentState.value.text
            },
            modifier = Modifier.fillMaxSize())
    }
}