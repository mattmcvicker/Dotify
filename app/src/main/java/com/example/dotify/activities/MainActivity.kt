package com.example.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.dotify.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        const val ARTIST_KEY = "ARTIST_KEY"
        const val SONG_KEY = "SONG_KEY"
        const val LARGE_ID = "LARGE_ID"
        const val RANDOM_NUMBER = "RANDOM_NUMBER"
    }
    private var randomNumber = Random.nextInt(1000, 9999999)
    private var saveInstanceAuthor = ""
    private var saveInstanceSong = ""
    private var saveInstanceLargeID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) { //
            with(savedInstanceState) {
                saveInstanceAuthor = getString(ARTIST_KEY, "")
                saveInstanceSong = getString(SONG_KEY, "")
                saveInstanceLargeID = getInt(LARGE_ID, -1)
                randomNumber = getInt(RANDOM_NUMBER, -1)
            }
        } else {
            saveInstanceAuthor = ""
            saveInstanceSong = ""
            saveInstanceLargeID = 0
        }
        author.text = intent.getStringExtra(ARTIST_KEY) //when activity launches, grab intent that launched us, and grab the values with the keys
        song.text = intent.getStringExtra(SONG_KEY)
        song.isSelected = true
        imageView.setImageResource(intent.getIntExtra(LARGE_ID, 0))
        randomGenerator()
        play.setOnClickListener { v: View ->
            randomNumber++;
            randomGenerator();
            Log.i("mattmcv", "CLicky");

        }

        next.setOnClickListener {v: View ->
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
            Log.i("mattmcv", "CLicky");
        }
        previous.setOnClickListener {v: View ->
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) { // make work accross all activiites
        outState.putCharSequence(SONG_KEY, author.text)
        outState.putCharSequence(ARTIST_KEY, song.text)
        outState.putInt(
            LARGE_ID, intent.getIntExtra(
                LARGE_ID, 0))
        outState.putInt(RANDOM_NUMBER, randomNumber)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun randomGenerator() {
        val randomPlays = findViewById<TextView>(R.id.numPlays);
        numPlays.text = "$randomNumber Plays";
    }
}

