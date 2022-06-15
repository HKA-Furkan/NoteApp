package com.example.noteapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Query("Select * From notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("Select * from notes where id = :input")
    fun getNote(input: String): Flow<Note>

    @Update
    suspend fun updateNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)


}