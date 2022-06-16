package com.example.noteapp

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteDAO: NoteDAO) {

    fun getNotes(): Flow<List<Note>> = noteDAO.getAllNotes()

    suspend fun saveNote(note: Note) {
        noteDAO.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDAO.deleteNote(note)
    }

}