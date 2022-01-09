package com.slavikodev.random_music_genre

import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.slavikodev.random_music_genre.databinding.ActivityMainBinding
import com.slavikodev.random_music_genre.db.Database
import com.slavikodev.random_music_genre.db.DatabaseRoomImpl
import java.net.URLEncoder

class MainActivity : AppCompatActivity(), View.OnClickListener, MainActivityPresenter.View {

    private lateinit var presenter: MainActivityPresenter
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.play.setOnClickListener(this)
        binding.next.setOnClickListener(this)
        binding.addToFavorites.setOnClickListener(this)
        binding.switchToFavorites.setOnClickListener(this)

        database = DatabaseRoomImpl.getDatabase(this)

        val bufferReader = resources.openRawResource(R.raw.genres).bufferedReader()
        val genresString = bufferReader.use {
            it.readText()
        }
        presenter = MainActivityPresenter(this, database, genresString)
    }

    override fun setGenreViewText(genreName: String) {
        binding.genre.text = genreName
    }

    override fun log(tag: String, message: String) {
        Log.wtf(tag, message)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.next.id -> presenter.showRandomGenre()

            binding.addToFavorites.id -> presenter.addToFavorites(binding.genre.text)

            binding.switchToFavorites.id -> presenter.switchToFavorites()

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
        }
    }

    companion object {
        const val LOG_TAG = "MainActivity"
        const val PLAY_APP_PKG_NAME = "com.google.android.apps.youtube.music"
        const val PLAY_APP_URI = "https://music.youtube.com"
    }

}