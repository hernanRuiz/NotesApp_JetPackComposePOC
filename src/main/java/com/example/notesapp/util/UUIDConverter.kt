package com.example.notesapp.util

import androidx.room.TypeConverter
import java.util.UUID


class UUIDConverter {

    @TypeConverter //Para guardar el id en la base de datos
    fun stringFromUUID(id: UUID): String {
        return id.toString()
    }

    @TypeConverter //Vuelve a cambiar el type a UUID cuando lo traemos de la base
    fun uuidFromString(id: String): UUID {
        return UUID.fromString(id)
    }
}