package com.slavikodev.random_music_genre

import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.slavikodev.random_music_genre.databinding.ActivityMainBinding
import com.slavikodev.random_music_genre.db.Database
import com.slavikodev.random_music_genre.db.DatabaseRoomImpl
import com.slavikodev.random_music_genre.db.room.Genre
import java.net.URLEncoder
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val PLAY_APP_PKG_NAME = "com.google.android.apps.youtube.music"
        const val PLAY_APP_URI = "https://music.youtube.com"
        const val LOG_TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: Database
    private lateinit var genres: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.play.setOnClickListener(this)
        binding.next.setOnClickListener(this)
        binding.addToFavorites.setOnClickListener(this)
        binding.switchToFavorites.setOnClickListener(this)

        database = DatabaseRoomImpl.getDatabase(this)
        initialLoadFavorites()
        genres = database.getAll()
        binding.genre.text = randomGenre
    }

    private fun initialLoadFavorites() {
        if (database.getAll().isEmpty()) {
            val bufferReader = resources.openRawResource(R.raw.genres).bufferedReader()
            val genresString = bufferReader.use {
                it.readText()
            }
            genres = genresString
                .split("\n")
                .filter {
                        x -> x.isNotEmpty()
                }
            for (genre in genres) {
                database.insert(genre)
            }
        }
    }

    private var isFavorites = false

    private val randomGenre get() = genres[Random.nextInt(genres.size)]

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.next.id -> {
                binding.genre.text = randomGenre
            }

            binding.play.id -> {
                // Try to start "Youtube Music" app.
                val launchIntent = packageManager.getLaunchIntentForPackage(PLAY_APP_PKG_NAME)
                launchIntent?.let {
                    // Create uri with search query.
                    val encodedGenreName = URLEncoder.encode(binding.genre.text as String, "utf-8")
                    it.data = Uri.parse("$PLAY_APP_URI/search?q=$encodedGenreName")
                    startActivity(it)
                    Log.wtf(LOG_TAG, "launchIntent is OK")
                } ?: run {
                    Log.wtf(LOG_TAG, "launchIntent is Null")
                }
            }

            binding.addToFavorites.id -> {
                val genreName = binding.genre.text as String
                database.update(genreName, true)
            }

            binding.switchToFavorites.id -> {
                if (isFavorites) {
                    isFavorites = false
                    genres = database.getAll()
                    binding.genre.text = randomGenre
                } else {
                    isFavorites = true
                    val favoriteGenres = database.getFavorites()
                    if (favoriteGenres.isNotEmpty()) {
                        genres = database.getFavorites()
                        binding.genre.text = randomGenre
                    }
                }
            }
        }
    }

}