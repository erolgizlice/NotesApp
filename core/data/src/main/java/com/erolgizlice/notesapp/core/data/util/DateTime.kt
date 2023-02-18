package com.erolgizlice.notesapp.core.data.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.erolgizlice.notesapp.core.ui.AlarmBroadcastReceiver
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun getDateTime(timeStamp: Long?, format: String): String? {
    timeStamp?.let {
        return SimpleDateFormat(format).format(Date(Timestamp(timeStamp).time))
    }
    return null
}

fun setAlarm(
    context: Context,
    calendar: Calendar,
    title: String,
    content: String
) {
    val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    val intent = Intent(context, AlarmBroadcastReceiver::class.java)
    intent.putExtra("title", title)
    intent.putExtra("content", content)
    val pendingIntent =
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

    alarmManager?.setExact(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}