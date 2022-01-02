package com.slavikodev.random_music_genre

import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.slavikodev.random_music_genre.databinding.ActivityMainBinding
import java.net.URLEncoder

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val PLAY_APP_PKG_NAME = "com.google.android.apps.youtube.music"
        const val PLAY_APP_URI = "https://music.youtube.com"
        const val LOG_TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var genreModel: GenreModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        genreModel = GenreModel(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.play.setOnClickListener(this)
        binding.next.setOnClickListener(this)
        binding.genre.text = genreModel.randomGenre
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.next.id -> binding.genre.text = genreModel.randomGenre
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

}