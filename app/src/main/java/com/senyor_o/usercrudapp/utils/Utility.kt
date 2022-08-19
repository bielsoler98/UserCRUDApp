package com.senyor_o.usercrudapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun String.getYear(): Int? {
    if(this.isNotEmpty()) {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(this)
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().year
        }
    }
    return null
}

fun String.getMonth(): Int? {
    if(this.isNotEmpty()) {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(this)
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().monthValue
        }
    }
    return null
}

fun String.getDay(): Int? {
    if(this.isNotEmpty()) {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(this)
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth
        }
    }
    return null
}

fun String.getFormattedDate(): String {
    if(this.isNotEmpty()) {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(this)
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        }
    }
    return ""
}