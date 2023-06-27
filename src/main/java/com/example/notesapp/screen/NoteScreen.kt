package com.example.notesapp.screen

import android.widget.Toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    val topBarColor = 0xFFDADFE3

    //View content
    Column(modifier = Modifier.padding(6.dp)) {

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name_sp)) },
            actions = {Icon(imageVector = Icons.Rounded.Notifications, contentDescription = ICON)},
            backgroundColor = Color(topBarColor))

        Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally){

            NoteInputText(
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp),
                text = title,
                label = stringResource(id = R.string.note_title),
                onTextChange = {
                    if(it.all { char -> char.isLetter() || char.isWhitespace() || char.isDigit() }){
                        title = it
                    }
                })

            NoteInputText(
                modifier = Modifier.padding(top = 9.dp, bottom = 16.dp),
                text = description,
                label = stringResource(id = R.string.note_description),
                onTextChange = {
                    if(it.all { char -> char.isLetter() || char.isWhitespace() || char.isDigit()  }){
                        description = it
                    }
                })

            NoteCheckBox(
                modifier = Modifier.padding(bottom = 16.dp),
                checked = isImportant,
                onCheckedChange = { checked_ ->
                    isImportant = checked_
                },
                label = stringResource(id = R.string.mark_as_important)
            )

            NoteButton(
                text = stringResource(id = R.string.save_note),
                onClick = {
                    if(title.isNotEmpty() && description.isNotEmpty()){
                        var toastStringId = successNoteSaveID
                        if(isUpdate){
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

        LazyColumn {
            items(notes){ note ->
                NoteRow(note = note,
                    onNoteClicked = {
                        onRemoveNote(note)
                    },
                    onNoteEdited = {
                        title = note.title
                        description = note.description
                        isImportant = note.isImportant
                        id = note.id
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
            .clip(RoundedCornerShape(16.dp))
            .padding(4.dp),
        color = Color(noteColor),
        elevation = 6.dp)
    {
        Column(modifier = Modifier
            .fillMaxHeight()
            .padding(start = 10.dp)
            .padding(
                top = 20.dp, end = 10.dp
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            val alphaValue = if (note.isImportant) 1f else 0f
            Image(
                painterResource(R.drawable.important_icon),
                contentDescription = stringResource(id = R.string.mark_as_important_tag),
                modifier = Modifier.alpha(alphaValue),
                contentScale = ContentScale.Inside
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Column(
                modifier = Modifier
                    .padding(start = 40.dp)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
                    .weight(8f),
                horizontalAlignment =  Alignment.Start){
                Text(modifier = Modifier.fillMaxWidth(),text = note.title, style = MaterialTheme.typography.subtitle2)
                Text(modifier = Modifier.fillMaxWidth(), text = note.description, style = MaterialTheme.typography.subtitle1)
                Text(modifier = Modifier.fillMaxWidth(), text = formatDate(note.entryDate.time, datePattern, dateLanguage), style = MaterialTheme.typography.caption)
            }

            Column(modifier = Modifier
                .clickable {
                    isUpdate = true
                    onNoteEdited(note)
                }
                .wrapContentWidth(Alignment.End)
                .weight(1f)
                .padding(
                    top = 20.dp
                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ){
                Image(
                    Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_note),
                    modifier = Modifier,
                    contentScale = ContentScale.Inside
                )
            }

            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable { onNoteClicked(note) }
                .padding(
                    top = 20.dp, end = 10.dp
                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ){
                Image(
                    Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_note),
                    modifier = Modifier,
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