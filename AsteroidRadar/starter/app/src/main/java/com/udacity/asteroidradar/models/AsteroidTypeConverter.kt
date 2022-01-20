package com.udacity.asteroidradar.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AsteroidTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun asteroidToString(item: AsteroidFeed): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun stringToAsteroid(data: String): AsteroidFeed {
        val listType = object : TypeToken<AsteroidFeed>() {}.type
        return gson.fromJson(data, listType)
    }

//    @TypeConverter
//    fun resultToString(result: Result): String {
//        return gson.toJson(result)
//    }
//    @TypeConverter
//    fun stringToResult(data: String): Result {
//        val listType = object : TypeToken<Result>() {}.type
//        return gson.fromJson(data, listType)
//    }

}

