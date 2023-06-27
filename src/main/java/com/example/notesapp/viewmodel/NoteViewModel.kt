package com.example.notesapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.notesapp.model.Note

class NoteViewModel: ViewModel() {

    var noteList = mutableStateListOf<Note>()

    fun addNote(note: Note){
        noteList.add(note)
    }

    fun removeNote(note: Note){
        noteList.remove(note)
    }

    fun getAllNotes(): List<Note> {
        return noteList
    }
}