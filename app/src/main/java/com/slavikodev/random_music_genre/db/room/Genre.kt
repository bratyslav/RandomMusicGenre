package com.slavikodev.random_music_genre.db.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Genre {

    @PrimaryKey
    var name: String = ""
    var isFavorite: Boolean = false

}