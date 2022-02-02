package com.udacity.asteroidradar

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udacity.asteroidradar.models.AsteroidFeed

class AsteroidTypeConverter {
    var gson = Gson()
    @TypeConverter
    fun asteroidFeedToString(asteroid: Any): String {
        return gson.toJson(asteroid)
    }
    @TypeConverter
    fun stringToAsteroidFeed(data: String): Any {
        val listType = object: TypeToken<Any>()  {}.type
        return gson.fromJson(data, listType)
    }
}