package com.slavikodev.random_music_genre

import com.slavikodev.random_music_genre.db.Database

class MainActivityPresenter(private val view: View, database: Database, genresString: String) {

    private val genre = GenreModel(database, genresString)
    private var isFavorite = false

    init {
        showRandomGenre()
    }

    fun showRandomGenre() {
        view.setGenreViewText(genre.random(isFavorite))
    }

    fun addToFavorites(genreName: CharSequence) {
        genre.addToFavorites(genreName)
    }

    fun switchToFavorites() {
        if (isFavorite) {
            isFavorite = false
            view.setGenreViewText(genre.random(isFavorite))
        } else {
            isFavorite = true
            view.log(LOG_TAG, genre.random(isFavorite))
            view.setGenreViewText(genre.random(isFavorite))
        }
    }

    interface View {
        fun setGenreViewText(genreName: String)
        fun log(tag: String, message: String)
    }

    companion object {
        const val LOG_TAG = "MainActivityPresenter"
    }

}