package com.slavikodev.random_music_genre

import android.content.Context
import kotlin.random.Random

class GenreModel(context: Context) {

    private val genreList: List<String>
    val randomGenre get() = genreList[Random.nextInt(genreList.size)]

    init {
        // Reading and parsing a txt file with all genres.
        val bufferReader = context.resources.openRawResource(R.raw.genres).bufferedReader()
        val genreText = bufferReader.use {
            it.readText()
        }
        genreList = genreText
            .split("\n")
            .filter {
                x -> x.isNotEmpty()
            }
    }

}