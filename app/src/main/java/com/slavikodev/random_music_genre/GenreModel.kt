package com.slavikodev.random_music_genre

import com.slavikodev.random_music_genre.db.Database
import kotlin.random.Random

class GenreModel(private val database: Database, genresString: String) {

    private val allGenres get() = database.getAll()
    private val favoriteGenres get() = database.getFavorites()

    init {
        if (allGenres.isEmpty()) {
            val genres = genresString
                .split("\n")
                .filter {
                        x -> x.isNotEmpty()
                }
            for (genre in genres) {
                database.insert(genre)
            }
        }
    }

    fun random(isFavorite: Boolean): String {
        return if (isFavorite) {
            if (favoriteGenres.isNotEmpty()) {
                favoriteGenres[Random.nextInt(favoriteGenres.size)]
            } else {
                allGenres[Random.nextInt(allGenres.size)]
            }
        } else {
            allGenres[Random.nextInt(allGenres.size)]
        }
    }

    fun addToFavorites(genreName: CharSequence) {
        val genreNameString = genreName as String
        database.update(genreNameString, true)
    }

}