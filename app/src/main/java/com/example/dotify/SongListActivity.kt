package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.MainActivity.Companion.NAME_KEY
import kotlinx.android.synthetic.main.activity_song_list.*
import java.util.Collections.shuffle

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        //Below is list of songs and authors.
        var listOfSongs: List<Song> = SongDataProvider.getAllSongs()
        listOfSongs = listOfSongs.toMutableList()
        val songAdapter = SongAdapter(listOfSongs)

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
            miniplayertext.text = item.title + " - " + item.artist
            //val intent = Intent(this, MainActivity::class.java) //create intent
            //intent.putExtra(NAME_KEY, item) //give data, each assigned with a key
            //startActivity(intent) // start that activity
        }

        songList.adapter = songAdapter

    }
}
