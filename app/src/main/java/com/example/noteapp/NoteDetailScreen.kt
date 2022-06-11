package com.example.noteapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun NoteDetailScreen(note: Note, viewModel: NoteViewModel, navController: NavHostController) {

    val titleState = remember { mutableStateOf(TextFieldValue(note.title)) }
    val contentState = remember { mutableStateOf(TextFieldValue(note.content)) }

    val showDialog = remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (note.creation.isEmpty()) "Neue Notiz" else note.title)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                elevation = 12.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val title = titleState.value.text
                    val content = contentState.value.text
                    if (isValid(title, content)) {

                        if (note.creation.isNotEmpty()) {
                            viewModel.removeNote(note)
                        }

                        viewModel.addNote(title, content)
                        navController.navigateUp()

                    } else {
                        showDialog.value = true
                    }
                }
            ) {
                Icon(Icons.Filled.Done, "")
            }
        }
    ) {
        Column() {

            OutlinedTextField(
                value = titleState.value,
                onValueChange = {
                    titleState.value = it
                    //note.title = titleState.value.text
                },
                label = { Text("Überschrift") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )


            OutlinedTextField(
                value = contentState.value,
                onValueChange = {
                    contentState.value = it
                    //note.content = contentState.value.text
                },
                label = { Text("Inhalt") },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            )

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog.value = false
                    },
                    title = {
                        Text("Fehler")
                    },
                    text = {
                        Text("Überschrift und Inhalt dürfen nicht leer sein!")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog.value = false
                            }) {
                            Text("Ok")
                        }
                    }
                )
            }
        }
    }
}

fun isValid(title: String, content: String): Boolean {
    return title.isNotEmpty() && content.isNotEmpty()
}