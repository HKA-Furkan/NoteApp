package com.example.noteapp

import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.noteapp.ui.theme.Purple700

@Composable
fun NoteDetailScreen(note: Note, viewModel: NoteViewModel, navController: NavHostController) {

    val titleState = remember { mutableStateOf(TextFieldValue(note.title)) }
    val contentState = remember { mutableStateOf(TextFieldValue(note.content)) }

    val showDialog = remember { mutableStateOf(false) }


    Scaffold(
        backgroundColor = Color(248,248,255),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (note.creation.isEmpty()) "Neue Notiz anlegen" else note.title,
                        fontSize = 32.sp,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                backgroundColor = Purple700,
                contentColor = Color.White,
                elevation = 12.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Purple700,

                onClick = {
                    val title = titleState.value.text
                    val content = contentState.value.text
                    if (isValid(title, content)) {

                        if (note.creation.isNotEmpty()) {
                            viewModel.deleteNote(note)
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
                },
                label = {
                    Text("Überschrift",
                        color = Color.DarkGray,
                    )},
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(253,245,230)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )


            OutlinedTextField(
                value = contentState.value,
                onValueChange = {
                    contentState.value = it
                },
                label = {
                    Text("Inhalt",
                        color = Color.DarkGray
                    )},
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(253,245,230)),
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
                        Text("Fehler",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp)
                    },

                    text = {
                        Text("Überschrift und Inhalt dürfen nicht leer sein!",
                            color = Color.Red)
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