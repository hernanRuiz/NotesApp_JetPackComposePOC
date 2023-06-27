package com.example.notesapp.util

import androidx.room.TypeConverter
import java.util.UUID


class UUIDConverter {

    @TypeConverter
    fun stringFromUUID(id: UUID): String {
        return id.toString()
    }

    @TypeConverter
    fun uuidFromString(id: String): UUID {
        return UUID.fromString(id)
    }
}