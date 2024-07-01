package com.example.weathercheck2000.data.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentDateInCustomFormat(): String{
    val currentDate = Date()
    val dateFormat = SimpleDateFormat("dd. MM. yyyy", Locale.getDefault())
    return dateFormat.format(currentDate)
}