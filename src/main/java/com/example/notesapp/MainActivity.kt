package com.example.notesapp

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.notesapp.data.NotesDataSource
import com.example.notesapp.screen.NoteScreen
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.viewmodel.NoteViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val noteViewModel: NoteViewModel by viewModels()
                    NotesApp(noteViewModel) //la app en si
                }
            }
        }
    }
}

@Composable
fun NotesApp(notesViewModel: NoteViewModel){
    //trae las notas que se persisten en la base de datos
    val noteList = notesViewModel.noteList.collectAsState().value

    //Setea la visual y métodos asociados
    NoteScreen(notes = noteList,
        onRemoveNote = { notesViewModel.removeNote(it) },
        onAddNote = { notesViewModel.addNote(it) },
        onUpdateNote = {notesViewModel.updateNote(it)}
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesAppTheme {
        NoteScreen(notes = NotesDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {}, onUpdateNote = {})
    }
}