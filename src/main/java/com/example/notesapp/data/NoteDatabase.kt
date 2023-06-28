package com.example.notesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.notesapp.model.Note
import com.example.notesapp.util.DateConverter
import com.example.notesapp.util.UUIDConverter

//Definimos la base de datos y le pasamos los typeConverters necesarios
@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}