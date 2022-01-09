package com.slavikodev.random_music_genre.db

interface Database {

    fun insert(genreName: String)

    fun update(genreName: String, newVal: Boolean)

    fun getAll(): List<String>

    fun getFavorites(): List<String>

}