package com.comic.fetch.database

import androidx.room.TypeConverter
import java.util.Calendar

class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

    @TypeConverter
    fun stringListToString(list: List<String>): String {
        return list.joinToString(separator = "$$")
    }

    @TypeConverter
    fun stringToStringList(value: String): List<String> {
        return value.split("$$").map { it.trim() }
    }
}
