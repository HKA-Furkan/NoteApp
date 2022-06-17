package com.example.noteapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.noteapp.ui.theme.Purple700


@Composable
fun NavigationGraph(
    viewModel: NoteViewModel,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.OverviewRoute.routeTemplate
) {

    val notes by viewModel.notes.observeAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.OverviewRoute.routeTemplate) {
            OverviewScreen(navController, viewModel, notes?: emptyList())
        }

        composable(Route.DetailsRoute.routeTemplate) { backStackEntry ->
            val noteJson = backStackEntry.arguments?.getString("note")
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(Note::class.java).lenient()
            val noteObject = jsonAdapter.fromJson(noteJson)

            if (noteObject != null) {
                NoteDetailScreen(noteObject, viewModel, navController)
            }
        }
    }
}

@Composable
fun OverviewScreen(
    navController: NavHostController,
    viewModel: NoteViewModel,
    notes: List<Note>
) {
    Scaffold(
        backgroundColor = Color(248,248,255),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Alle Notizen", fontSize = 32.sp)
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

                    val note = Note(title = "", content = "", creation = "")
                    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter = moshi.adapter(Note::class.java).lenient()
                    val noteJson = jsonAdapter.toJson(note)

                    navController.navigate(Route.DetailsRoute.createRoute(noteJson))
                }
            ) {
                Icon(Icons.Filled.Add, "")
            }
        },
        content = {

            LazyColumn(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {
                items(notes) { note ->
                    NoteCard(note, viewModel) {

                        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                        val jsonAdapter = moshi.adapter(Note::class.java).lenient()
                        val noteJson = jsonAdapter.toJson(note)

                        navController.navigate(Route.DetailsRoute.createRoute(noteJson))
                    }
                }
            }
        })
}

@Composable
fun NoteCard(note: Note, viewModel: NoteViewModel, onClick: () -> Unit) {
    Card(
        backgroundColor = Color(253,245,230),
        modifier = Modifier
            .heightIn(0.dp, 156.dp)
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(15.dp)) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(note.title, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                Text("Letzte Änderung: " + note.creation, fontStyle = FontStyle.Companion.Italic, color = Color(112,128,144))
                Text(note.content, modifier = Modifier.padding(top = 5.dp))
            }

            IconButton(onClick = {
                viewModel.deleteNote(note)
            }, modifier = Modifier.align(CenterVertically)) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    "",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Red
                )
            }

        }
    }
}
