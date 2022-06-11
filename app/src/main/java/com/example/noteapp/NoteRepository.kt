package com.example.noteapp

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor() {

    val testNotes = mutableListOf<Note>()

    fun getNotes(): List<Note> {
        if (testNotes.isEmpty()) {
            for (i in 1..10) {
                testNotes.add(Note("Titel $i", "Das ist Test $i", ""))
            }
        }
        return testNotes
    }
}