package com.example.notesapp.data

import com.example.notesapp.model.Note

//Dummy data para los @Preview
class NotesDataSource {

    fun loadNotes(): List<Note> {
        return listOf(
            Note(title = "Día de película", description = "Mirar película en familia", isImportant = true),
            Note(title = "Vacaciones", description = "Inicio de vacaciones")
        )
    }
}