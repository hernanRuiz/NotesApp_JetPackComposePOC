package com.example.notesapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.UUID
import java.util.Date

//Entidad que representa cada objeto a guardar en la base de datos
@Entity(tableName = "notes_tbl")
data class Note(

    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "note_title")
    val title: String,

    @ColumnInfo(name = "note_description")
    val description: String,

    @ColumnInfo(name = "note_entry_date")
    val entryDate: Date = Date.from(Instant.now()),

    @ColumnInfo(name = "note_important_mark")
    val isImportant: Boolean = false
)