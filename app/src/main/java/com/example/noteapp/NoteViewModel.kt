package com.example.noteapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val notes = MutableLiveData<List<Note>>()

    init {
        viewModelScope.launch {
            repository.getNotes().collect {
                notes.postValue(it)
            }
        }
    }

    fun addNote(title: String, content: String) {
        val note = Note(
            title = title,
            content = content,
            creation = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Date())
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

}