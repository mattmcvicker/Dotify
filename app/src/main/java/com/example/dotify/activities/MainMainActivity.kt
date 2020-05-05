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
import kotlinx.android.synthetic.main.activity_main_main.miniplayertext
import kotlinx.android.synthetic.main.activity_song_list.*
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
        if (supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) == null) {
            val songListFragment = SongListFragment.newInstance(ArrayList(listOfSongs))

            supportFragmentManager
                .beginTransaction() //
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG) //add fragment conatiner, and the fragment
                .commit()
        } else {

        }

         supportFragmentManager.addOnBackStackChangedListener {
             val hasBackStack =supportFragmentManager.backStackEntryCount > 0
             if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
             } else {
                 supportActionBar?.setDisplayHomeAsUpEnabled(false)
                 miniPlayerVisible()
             }
         }
    }

     override fun onSupportNavigateUp(): Boolean {
         supportFragmentManager.popBackStack()
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
                     .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                     .addToBackStack(NowPlayingFragment.TAG)
                     .commit()
         }
     }

     private fun miniPlayerVisible() {
         miniPlayer.setVisibility(View.VISIBLE)
         btnShuffle.setVisibility(View.VISIBLE)
         miniplayertext.setVisibility(View.VISIBLE)
     }
}
