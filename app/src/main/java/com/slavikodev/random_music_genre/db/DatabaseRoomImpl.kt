package com.slavikodev.random_music_genre.db

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.slavikodev.random_music_genre.db.room.Genre
import com.slavikodev.random_music_genre.db.room.GenreRoomDatabase

class DatabaseRoomImpl(private val roomDatabase: GenreRoomDatabase): Database {

    companion object {

        private val LOG_TAG = "DatabaseRoomImpl"
        private var INSTANCE: Database? = null

        fun getDatabase(context: Context): Database {
            return INSTANCE ?: synchronized(this) {
                val roomDatabase = Room.databaseBuilder(context, GenreRoomDatabase::class.java, "db")
                    .allowMainThreadQueries()
                    .build()
                val db = DatabaseRoomImpl(roomDatabase)
                INSTANCE = db
                return db
            }
        }

    }

    override fun insert(genreName: String) {
        val genre = Genre().apply {
            name = genreName
            isFavorite = false
        }
        try {
            roomDatabase.genreDao.insert(genre)
        } catch (error: Exception) {
            Log.wtf(LOG_TAG, error.localizedMessage)
        }
    }

    override fun update(genreName: String, newVal: Boolean) {
        val genre = Genre().apply {
            name = genreName
            isFavorite = newVal
        }
        try {
            roomDatabase.genreDao.update(genre)
        } catch (error: Exception) {
            Log.wtf(LOG_TAG, error.localizedMessage)
        }
    }

    override fun getAll(): List<String> {
        val genres = roomDatabase.genreDao.getAll()
        val genresNames = genres.map {
            it.name
        }
        return genresNames
    }

    override fun getFavorites(): List<String> {
        val genres = roomDatabase.genreDao.getFavorites()
        val genresNames = genres.map {
            it.name
        }
        return genresNames
    }

}