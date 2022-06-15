package com.example.noteapp

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseProvider {

    @Provides
    @Singleton
    fun getNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, "notesDB").build()
    }

    @Provides
    @Singleton
    fun getNoteDao(noteDatabase: NoteDatabase): NoteDAO = noteDatabase.noteDao()
}