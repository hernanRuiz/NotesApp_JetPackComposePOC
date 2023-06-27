package com.example.notesapp.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Arrangement
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
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isImportant by remember {mutableStateOf(false)}
    val successNoteSaveID = stringResource(id = R.string.note_save_success)

    //View content
    Column(modifier = Modifier.padding(6.dp)) {

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name_sp)) },
            actions = {Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "Icon")},
            backgroundColor = Color(0xFFDADFE3))

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
                        onAddNote(Note(title = title, description = description, isImportant = isImportant))
                        title = ""
                        description = ""
                        isImportant = false
                        Toast.makeText(context, successNoteSaveID, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        Divider(modifier = Modifier.padding(10.dp))

        LazyColumn {
            items(notes){ note ->
                NoteRow(note = note, onNoteClicked = {
                    onRemoveNote(it)
                })
            }
        }
    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit
){
    val datePattern = stringResource(id = R.string.date_pattern)
    val dateLanguage = stringResource(id = R.string.language)

    Surface(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .padding(4.dp)
            .fillMaxWidth(),
        color = Color(0xFFDFE6EB),
        elevation = 6.dp)
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onNoteClicked(note) },
            horizontalArrangement = Arrangement.SpaceBetween) {

            Column(
                modifier
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalAlignment =  Alignment.Start){
                Text(text = note.title, style = MaterialTheme.typography.subtitle2)
                Text(text = note.description, style = MaterialTheme.typography.subtitle1)
                Text(text = note.entryDate.format(DateTimeFormatter.ofPattern(datePattern, Locale(dateLanguage, dateLanguage.uppercase()))),
                    style = MaterialTheme.typography.caption)
            }
        }
        Column(modifier = Modifier
            .fillMaxHeight()
            .padding(
                top = 20.dp, end = 10.dp
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ){
            val alphaValue = if (note.isImportant) 1f else 0f
            Image(
                painterResource(R.drawable.important_icon),
                contentDescription = "important tag",
                modifier = Modifier.alpha(alphaValue),
                contentScale = ContentScale.Inside
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen(notes = NotesDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {})
}