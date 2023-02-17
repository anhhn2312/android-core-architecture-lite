package com.andyha.corearchitecture.database

import androidx.room.TypeConverter
import com.andyha.coreextension.toJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class DateConverter {
    @TypeConverter
    fun toDbValue(date: Date? = null): Long? = date?.time

    @TypeConverter
    fun fromDbToValue(date: Long? = 0): Date? = date?.let { Date(it) }
}

class StringArrayConverter {
    @TypeConverter
    fun toDbValue(data: List<String>? = null): String? = data?.toJson()

    @TypeConverter
    fun fromDbToValue(data: String? = null): List<String>? = data?.let {
        Gson().fromJson(it, object : TypeToken<List<String>>() {}.type)
    }
}

class StringToLongConverter {
    @TypeConverter
    fun toDbValue(data: String? = null): String? = data

    @TypeConverter
    fun fromDbToValue(time: String? = null): Long? = time?.let {
        it.toLong()
    }
}