package com.example.notesapp.di

import android.content.Context

import androidx.room.Room

import com.example.notesapp.data.NoteDatabase
import com.example.notesapp.data.NoteDatabaseDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val PROVIDER_NAME = "notes_db"

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDatabaseDao = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase
            = Room.databaseBuilder(
                context,
                NoteDatabase::class.java,
                PROVIDER_NAME)
        .fallbackToDestructiveMigration()
        .build()
}