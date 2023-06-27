package com.example.notesapp.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy

import com.example.notesapp.model.Note

import kotlinx.coroutines.flow.Flow


@Dao //Contecta mi entidad Note con la base de datos
interface NoteDatabaseDao {

    @Query("SELECT * from notes_tbl")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * from notes_tbl where id =:id")
    suspend fun getNoteById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE )
    suspend fun update(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}