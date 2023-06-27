package com.example.notesapp.data

import com.example.notesapp.model.Note


class NotesDataSource {

    fun loadNotes(): List<Note> {
        return listOf(
            Note(title = "Día de película", description = "Mirar película en familia"),
            Note(title = "Vacaciones", description = "Inicio de vacaciones")
        )
    }
}