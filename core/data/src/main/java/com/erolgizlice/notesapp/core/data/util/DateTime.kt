package com.erolgizlice.notesapp.core.data.util

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun getDateTime(timeStamp: Long?): String? {
    timeStamp?.let {
        return SimpleDateFormat("dd/MM/yyyy").format(Date(Timestamp(timeStamp).time))
    }
    return null
}