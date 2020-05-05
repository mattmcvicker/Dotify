package com.example.dotify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.R
import com.example.dotify.SongAdapter
import com.example.dotify.activities.MainActivity.Companion.LARGE_ID
import com.example.dotify.activities.MainActivity.Companion.ARTIST_KEY
import com.example.dotify.activities.MainActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = "All Songs"
        //Below is list of songs and authors.
        var listOfSongs: List<Song> = SongDataProvider.getAllSongs()
        listOfSongs = listOfSongs.toMutableList()
        val songAdapter = SongAdapter(listOfSongs)
        var currentSong = SongDataProvider.createRandomSong()
        ///////////////////////////////////////////////////////////////////
        btnShuffle.setOnClickListener{
            val newSongs = listOfSongs.apply {
                shuffle()
            }.toMutableList()
            songAdapter.change(newSongs)
        }
        //////////////////////////////////////////////////////////////////
        songAdapter.onSongClickListener = { item ->
            ///// This is the object that is actually being used
            Log.i("mattmcv", item.toString());
            miniplayertext.text = item.title + " - " + item.artist //////////INSERT MAYBE
            currentSong = item
        }

        songList.adapter = songAdapter
        ////////////////////////////////////////////////////////////////////////

        miniPlayer.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) //create intent
            Log.i("mattmcv", currentSong.toString());
            intent.putExtra(ARTIST_KEY, currentSong.artist) //give data, each assigned with a key
            intent.putExtra(SONG_KEY, currentSong.title) //give data, each assigned with a key
            intent.putExtra(LARGE_ID, currentSong.largeImageID)
            startActivity(intent) // start that activity with that data
        }
    }
}
