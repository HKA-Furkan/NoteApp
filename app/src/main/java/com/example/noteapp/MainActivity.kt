package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Mainpage()
            }
        }
    }
}

@Composable
fun Mainpage(
    viewModel: NoteViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Notizen")
            },
            actions = {
                IconButton(onClick = { viewModel.addNote("test", "content") }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                }
            },
            backgroundColor = Color.Blue,
            contentColor = Color.White,
            elevation = 12.dp
        )
    }, content = {
        // A surface container using the 'background' color from the theme
        /*Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Greeting("Android")
        }*/
        NavigationGraph(viewModel)
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteAppTheme {
        Mainpage()
    }
}