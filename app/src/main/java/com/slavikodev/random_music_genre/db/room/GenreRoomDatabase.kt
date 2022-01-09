package com.slavikodev.random_music_genre.db.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Genre::class], version = 1)
abstract class GenreRoomDatabase: RoomDatabase() {

    abstract val genreDao: GenreDao

}