package com.example.notesapp.util

import androidx.room.TypeConverter
import java.util.Date


class DateConverter {

    @TypeConverter //Para guardar la fecha en la base de datos
    fun timeStampFromDate(date: Date) : Long{
        return date.time
    }

    @TypeConverter //Devuelve objeto que se va a formatear para cambiar la forma en la que se va a mostrar la fecha en pantalla
    fun dateFromTimeStamp(timeStamp: Long) : Date{
        return Date(timeStamp)
    }
}