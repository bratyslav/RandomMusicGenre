package com.slavikodev.random_music_genre.db.room

import androidx.room.*

@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    fun getAll(): List<Genre>

    @Query("SELECT * FROM genre where isFavorite = 1")
    fun getFavorites(): List<Genre>

    @Insert
    fun insert(genre: Genre)

    @Update
    fun update(genre: Genre)

}