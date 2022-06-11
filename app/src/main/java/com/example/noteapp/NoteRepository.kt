package com.example.noteapp

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor() {

    private val testNotes = mutableListOf<Note>()

    fun getNotes(): List<Note> {
        if (testNotes.isEmpty()) {
            for (i in 1..3) {
                val now = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Date())
                testNotes.add(Note("Titel $i", "Das ist Test $i", now))
            }
        }
        return testNotes
    }

    fun addNote(note: Note) {
        testNotes.add(note)
    }

    fun removeNote(note: Note) {
        testNotes.remove(note)
    }
}