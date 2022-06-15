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
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Alle Notizen")
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                elevation = 12.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onClick() },
    ) {
        Row(modifier = Modifier.padding(15.dp)) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(note.title)
                Text(note.creation)
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

/*@Preview(showBackground=true)
@Composable
fun OverviewScreenPreview() {
    NavigationGraph()
}*/