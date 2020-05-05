package com.example.dotify.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.R
import com.example.dotify.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_now_playing.*
import java.util.ArrayList
import kotlin.random.Random

class NowPlayingFragment : Fragment() {
    companion object {
        //var currentSong = SongDataProvider.createRandomSong()
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val SONG_NAME = "SONG"
        const val SONG_ARTIST = "ARTIST"
        const val SONG_ID = "ID"
        const val RANDOM_COUNT = "RANDOM"
        const val ARTIST_KEY = "ARTIST_KEY"
        const val SONG_KEY = "SONG_KEY"
        const val LARGE_ID = "LARGE_ID"

        fun newInstance(currentSong: Song): NowPlayingFragment {
            val args = Bundle().apply {
                putString(SONG_NAME, currentSong.title)
                putString(SONG_ARTIST, currentSong.artist)
                putInt(SONG_ID, currentSong.largeImageID)
            }

            return NowPlayingFragment().apply {
                setArguments(args)
            }
        }

    }
    private var randomNumber = 0
    private var saveInstanceAuthor = ""
    private var saveInstanceSong = ""
    private var saveInstanceLargeID = 0

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(RANDOM_COUNT, randomNumber)
        super.onSaveInstanceState(outState)
    }
    override fun onCreate(savedInstanceState: Bundle?) { //for data before UI is shown
        super.onCreate(savedInstanceState)

        randomNumber = Random.nextInt(1000, 9999999)

        if (savedInstanceState != null) { //
            with(savedInstanceState) {
                saveInstanceAuthor = getString(ARTIST_KEY, "")
                saveInstanceSong = getString(SONG_KEY, "")
                saveInstanceLargeID = getInt(LARGE_ID, -1)
                randomNumber = getInt(RANDOM_COUNT, -1)
            }
        } else {
            saveInstanceAuthor = ""
            saveInstanceSong = ""
            saveInstanceLargeID = 0
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        author.text = intent.getStringExtra(MainActivity.ARTIST_KEY) //when activity launches, grab intent that launched us, and grab the values with the keys
//        song.text = intent.getStringExtra(MainActivity.SONG_KEY)
//        song.isSelected = true
//        imageView.setImageResource(intent.getIntExtra(MainActivity.LARGE_ID, 0))
        updateSong()
        randomGenerator()
        play.setOnClickListener { v: View ->
            randomNumber++;
            randomGenerator();
            Log.i("mattmcv", "CLicky");

        }

        next.setOnClickListener {v: View ->
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
            Log.i("mattmcv", "CLicky");
        }
        previous.setOnClickListener {v: View ->
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
    }
    fun randomGenerator() {
        //val randomPlays = findViewById<TextView>(R.id.numPlays);
        numPlays.text = "$randomNumber Plays";
    }

    fun updateSong() {
        author.text = arguments?.getString(SONG_ARTIST)
        song.text = arguments?.getString(SONG_NAME)
        arguments?.getInt(SONG_ID)?.let { imageView.setImageResource(it) }
    }

    fun reupdate(song: Song) {
        this.saveInstanceSong = song.title
        this.saveInstanceAuthor = song.artist
        this.saveInstanceLargeID = song.largeImageID
        updateSong()
    }


}
