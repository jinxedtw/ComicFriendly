package com.comic.fetch.database

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.Calendar

class Converters {

    private val moshi: Moshi = Moshi.Builder().build()

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

    @TypeConverter
    fun stringToHashMap(value: String): Map<String, String> {
        val mapType = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val adapter: JsonAdapter<Map<String, String>> = moshi.adapter(mapType)
        return adapter.fromJson(value) ?: emptyMap()
    }

    @TypeConverter
    fun hashMapToString(map: Map<String, String>): String {
        val mapType = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val adapter: JsonAdapter<Map<String, String>> = moshi.adapter(mapType)
        return adapter.toJson(map)
    }
}
