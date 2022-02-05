package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.PrimaryKey

class Constants {
    companion object {
        const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
        const val DEFAULT_END_DATE_DAYS = 7
        const val BASE_URL = "https://api.nasa.gov"
        const val API_KEY = "wYdtvRwTikmKomk89SgnTi4kC6DWFjZGHmgNMvD5"
        const val DATABASE_NAME = "asteroid_database"
    }
}