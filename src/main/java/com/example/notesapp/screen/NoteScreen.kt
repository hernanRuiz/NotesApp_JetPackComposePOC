package com.example.notesapp.screen

import android.widget.Toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.notesapp.R
import com.example.notesapp.components.NoteButton
import com.example.notesapp.components.NoteCheckBox
import com.example.notesapp.components.NoteInputText
import com.example.notesapp.data.NotesDataSource
import com.example.notesapp.model.Note
import com.example.notesapp.util.formatDate

import java.util.UUID


private var isUpdate = false
private lateinit var id : UUID
private const val ICON = "Icon"

//La UI de la app. En compose se define UI y comportamiento en un solo archivo
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onUpdateNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isImportant by remember {mutableStateOf(false)}
    val successNoteSaveID = stringResource(id = R.string.note_save_success)
    val successNoteUpdateID = stringResource(id = R.string.note_update_success)
    val successNoteDeleteID = stringResource(id = R.string.deleted_note)
    val topBarColor = 0xFFDADFE3

    Column(modifier = Modifier.padding(6.dp)) {

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name_sp)) },
            actions = {Icon(imageVector = Icons.Rounded.Notifications, contentDescription = ICON)},
            backgroundColor = Color(topBarColor))

        Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally){

            //Primer campo de texto: título
            NoteInputText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 9.dp, bottom = 8.dp, start = 60.dp, end = 60.dp)
                    .horizontalScroll(rememberScrollState(), reverseScrolling = true),
                text = title,
                label = stringResource(id = R.string.note_title),
                onTextChange = {
                    title = it
                })

            //Segundo campo de texto: descripción
            NoteInputText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 9.dp, bottom = 16.dp, start = 60.dp, end = 60.dp)
                    .horizontalScroll(rememberScrollState(), reverseScrolling = true),
                text = description,
                label = stringResource(id = R.string.note_description),
                onTextChange = {
                    description = it
                })

            //Checkbox que marca la nota como importante
            NoteCheckBox(
                modifier = Modifier.padding(bottom = 16.dp),
                checked = isImportant,
                onCheckedChange = { checked_ ->
                    isImportant = checked_
                },
                label = stringResource(id = R.string.mark_as_important)
            )

            //Botón para guardado o edición de nota
            NoteButton(
                text = stringResource(id = R.string.save_note),
                onClick = {
                    if(title.isNotEmpty() && description.isNotEmpty()){
                        var toastStringId = successNoteSaveID
                        if(isUpdate){
                            isUpdate = false
                            toastStringId = successNoteUpdateID
                            onUpdateNote(Note(id = id, title = title, description = description, isImportant = isImportant))
                        } else {
                            onAddNote(Note(title = title, description = description, isImportant = isImportant))
                        }
                        title = ""
                        description = ""
                        isImportant = false
                        Toast.makeText(context, toastStringId, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        Divider(modifier = Modifier.padding(10.dp))

        //Listado de notas
        LazyColumn {
            items(notes){ note ->
                NoteRow(note = note,
                    onNoteClicked = {
                        onRemoveNote(note)
                        Toast.makeText(context, successNoteDeleteID, Toast.LENGTH_SHORT).show()
                    },
                    onNoteEdited = {
                        title = note.title
                        description = note.description
                        isImportant = note.isImportant
                        id = note.id //en el update me llevo el id de la nota para identificarla en la base y modificarla
                    }
                )
            }
        }
    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit,
    onNoteEdited: (Note) -> Unit
){
    val datePattern = stringResource(id = R.string.date_pattern)
    val dateLanguage = stringResource(id = R.string.language)
    val noteColor = 0xFFDFE6EB

    Surface(
        modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .padding(4.dp),
        color = Color(noteColor),
        elevation = 6.dp)
    {
        Row(modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween){

            //Imagen que representa la nota marcada como importante
            Box(modifier = Modifier
                .fillMaxHeight()
                .align(alignment = Alignment.CenterVertically)
                .clickable { onNoteClicked(note) }
                .padding(start = 10.dp),
            ){
                val alphaValue = if (note.isImportant) 1f else 0f //para mostrar o no la imagen según corresponda
                Image(
                    painterResource(R.drawable.important_icon),
                    contentDescription = stringResource(id = R.string.mark_as_important_tag),
                    modifier = Modifier
                        .alpha(alphaValue)
                        .align(Alignment.CenterStart),
                    contentScale = ContentScale.Inside
                )
            }

            //Cuerpo de la nota: título, descripción y fecha
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 14.dp, vertical = 6.dp)
                    .weight(8f),
                horizontalAlignment =  Alignment.Start)
                {
                    Text(modifier = Modifier.fillMaxWidth(),text = note.title, style = MaterialTheme.typography.subtitle2)
                    Text(modifier = Modifier.fillMaxWidth(), text = note.description, style = MaterialTheme.typography.subtitle1)
                    Text(modifier = Modifier.fillMaxWidth(), text = formatDate(note.entryDate.time, datePattern, dateLanguage), style = MaterialTheme.typography.caption)
                }

            //Ícono para edición de nota
            Box(modifier = Modifier
                .fillMaxHeight()
                .align(alignment = Alignment.CenterVertically)
                .clickable {
                    isUpdate = true
                    onNoteEdited(note)
                }
                .padding(end = 6.dp)
                .weight(1f),
            ){
                Image(
                    Icons.Default.Edit,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    contentDescription = stringResource(id = R.string.edit_note),
                    contentScale = ContentScale.Inside
                )
            }

            //Ícono para eliminar nota
            Box(modifier = Modifier
                .fillMaxHeight()
                .align(alignment = Alignment.CenterVertically)
                .clickable { onNoteClicked(note) }
                .weight(1f)
                .padding(end = 10.dp, top = 2.dp),
            ){
                Image(
                    Icons.Default.Delete,
                    modifier = Modifier.align(alignment = Alignment.TopEnd),
                    contentDescription = stringResource(id = R.string.delete_note),
                    contentScale = ContentScale.Inside
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen(notes = NotesDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {}, onUpdateNote = {})
}