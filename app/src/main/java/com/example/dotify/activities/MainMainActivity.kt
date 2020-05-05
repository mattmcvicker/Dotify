 package com.example.dotify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.R
import com.example.dotify.fragments.NowPlayingFragment
import com.example.dotify.fragments.OnSongClickListener
import com.example.dotify.fragments.SongListFragment
import kotlinx.android.synthetic.main.activity_main_main.*
import kotlinx.android.synthetic.main.activity_song_list.btnShuffle
import kotlinx.android.synthetic.main.activity_song_list.miniPlayer

 class MainMainActivity : AppCompatActivity(), OnSongClickListener {
    private var currentSong = SongDataProvider.createRandomSong()
     //private lateinit var listOfSongs: List<Song>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_main)

        var listOfSongs: List<Song> = SongDataProvider.getAllSongs()
        listOfSongs = listOfSongs.toMutableList()

         miniPLayerStuff()

        val songListFragment = SongListFragment.newInstance(ArrayList(listOfSongs))
        //val songListFragment = SongListFragment(); //Fragment

        supportFragmentManager
            .beginTransaction() //
            .add(R.id.fragContainer, songListFragment) //add fragment conatiner, and the fragment
            .commit()

         supportFragmentManager.addOnBackStackChangedListener {
             val hasBackStack =supportFragmentManager.backStackEntryCount > 0
             if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
             } else {
                 supportActionBar?.setDisplayHomeAsUpEnabled(false)
             }
         }
    }

     override fun onSupportNavigateUp(): Boolean {
//         val nowPlayingFragment = supportFragmentManager.fragments.find { fragment -> fragment is NowPlayingFragment }

//         if(nowPlayingFragment != null) {
//             supportFragmentManager
//                 .beginTransaction()
//                 .remove(nowPlayingFragment)
//                 .commit()
//
//             return true
//         }
         supportFragmentManager.popBackStack()
         miniPlayer.setVisibility(View.VISIBLE)
         btnShuffle.setVisibility(View.VISIBLE)
         miniplayertext.setVisibility(View.VISIBLE)
         return super.onNavigateUp()
     }

     override fun onSongClicked(song: Song) {
         miniplayertext.text = song.title + " - " + song.artist //////////INSERT MAYBE
         currentSong = song
     }

     private fun miniPLayerStuff() {
         miniPlayer.setOnClickListener {
             //val intent = Intent(this, MainActivity::class.java) //create intent
             miniPlayer.setVisibility(View.GONE)
             btnShuffle.setVisibility(View.GONE)
             miniplayertext.setVisibility(View.GONE)
             val nowPlayingFragment = NowPlayingFragment.newInstance(currentSong)
             supportFragmentManager
                 .beginTransaction()
                 .add(R.id.fragContainer, nowPlayingFragment)
                 .addToBackStack(NowPlayingFragment.TAG)
                 .commit()
//             intent.putExtra(MainActivity.ARTIST_KEY, currentSong.artist) //give data, each assigned with a key
//             intent.putExtra(MainActivity.SONG_KEY, currentSong.title) //give data, each assigned with a key
//             intent.putExtra(MainActivity.LARGE_ID, currentSong.largeImageID)
//             startActivity(intent) // start that activity with that data
         }
     }
}
