package com.example.notesapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Formateo de fecha para mostrar en pantalla
fun formatDate(time: Long, dateFormat: String, dateLanguage: String): String
{
    val formatPattern = SimpleDateFormat(dateFormat, Locale(dateLanguage, dateLanguage.uppercase()))
    return formatPattern.format(Date(time))
}