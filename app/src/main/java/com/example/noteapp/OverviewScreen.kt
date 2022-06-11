package com.example.noteapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel


/*val testNotes = mutableMapOf<Int, Note>()
fun getNotes(): Map<Int, Note> {
    if (testNotes.isEmpty()) {
        for (i in 1..100) {
            testNotes[i] = Note("Titel $i", "Das ist Test $i", "")
        }
    }
    return testNotes
}*/

@Composable
fun NavigationGraph (
    viewModel: NoteViewModel,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.OverviewRoute.routeTemplate
) {

    val noteState by viewModel.notes.observeAsState()

    val notes = noteState?.notes ?: emptyList<Note>()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.OverviewRoute.routeTemplate) {
            OverviewScreen(navController, notes)
        }

        composable(Route.DetailsRoute.routeTemplate) { backStackEntry ->
            val noteJson = backStackEntry.arguments?.getString("note")
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(Note::class.java).lenient()
            val noteObject = jsonAdapter.fromJson(noteJson)

            if (noteObject != null) {
                NoteDetailScreen(noteObject)
            }
        }
    }
}

@Composable
fun OverviewScreen(
    navController: NavHostController,
    notes: List<Note>
    //notes: Map<Int, Note> = getNotes()
) {
    LazyColumn(modifier = Modifier
        .padding(4.dp)
        .fillMaxSize()
    ) {
        items(notes) { note ->
            NoteCard(note) {

                val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                val jsonAdapter = moshi.adapter(Note::class.java).lenient()
                val noteJson = jsonAdapter.toJson(note)

                navController.navigate(Route.DetailsRoute.createRoute(noteJson))
            }
        }
    }
}

@Composable
fun NoteCard(note: Note, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(note.title)
            Text(note.creation)
            Text(note.content, modifier = Modifier.padding(top = 5.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

/*@Preview(showBackground=true)
@Composable
fun OverviewScreenPreview() {
    NavigationGraph()
}*/