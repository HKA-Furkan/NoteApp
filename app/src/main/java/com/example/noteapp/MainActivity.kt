package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
        NavigationGraph(viewModel)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteAppTheme {
        Mainpage()
    }
}