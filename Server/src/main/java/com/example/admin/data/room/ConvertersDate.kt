package com.example.admin.data.room

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class ConvertersDate {
    private val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return value?.let { dateFormat.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }
}