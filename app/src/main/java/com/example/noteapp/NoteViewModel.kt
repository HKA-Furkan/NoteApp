package com.example.noteapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes: MutableLiveData<NoteState> = MutableLiveData(NoteState(emptyList()))
    val notes = _notes

    init {
        loadNotes()
    }

    private fun loadNotes() {
        val currentState = _notes.value
        if (currentState != null) {
            val result = repository.getNotes()
            _notes.value = currentState.copy(notes = result)
        }
    }

    fun addNote(title: String, content: String) {
        val currentState = _notes.value
        if (currentState != null) {
            val currentNotes = currentState.notes
            val now = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Date())
            val newNote = Note(title, content, now)
            _notes.value = currentState.copy(
                notes = currentNotes.plus(newNote)
            )

        }
    }

}