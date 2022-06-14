package com.example.noteapp

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteDAO: NoteDAO) {

    fun getNotes(): Flow<List<Note>> = noteDAO.getAllNotes()

    fun getNote(query: String): Flow<Note> = noteDAO.getNote(query)

    suspend fun saveNote(note: Note) {
        noteDAO.insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDAO.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDAO.deleteNote(note)
    }

    val testNotes = mutableListOf<Note>()

    fun getNotesTest(): List<Note> {
        if (testNotes.isEmpty()) {
            for (i in 1..10) {
                testNotes.add(Note(i.toLong(), "Titel $i", "Das ist Test $i", ""))
            }
        }
        return testNotes
    }
}