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
     companion object {
         const val KEEP_ORDER = "LIstorder"
         const val KEEP_MINI = "miniplayer"
         const val KEEP_SONGNAME = "songname"
         const val KEEP_SHUFFLE = "shuffle"
     }

     private var currentSong = SongDataProvider.createRandomSong()
     private var saveInstanceOrder = ""
     private var saveInstanceSong = ""

     override fun onSaveInstanceState(outState: Bundle) {
         outState.putCharSequence(KEEP_SONGNAME, saveInstanceSong)
         super.onSaveInstanceState(outState)

     }

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main_main)
//////////////////////////////
         if (savedInstanceState != null) { //
             with(savedInstanceState) {
                 saveInstanceSong = getString(KEEP_SONGNAME, "")
             }
         } else {
             saveInstanceSong = ""
         }

         var listOfSongs: List<Song> = SongDataProvider.getAllSongs()
         listOfSongs = listOfSongs.toMutableList()

         miniPLayerStuff()
         miniplayertext.text = saveInstanceSong
         if (supportFragmentManager.findFragmentByTag(SongListFragment.TAG) == null) {
             val songListFragment = SongListFragment.newInstance(ArrayList(listOfSongs))
             supportFragmentManager
                 .beginTransaction() //
                 .add(
                     R.id.fragContainer,
                     songListFragment,
                     SongListFragment.TAG
                 ) //add fragment conatiner, and the fragment
                 .commit()
         } else if (supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) != null) {
             miniPlayerInvisible()
             supportActionBar?.setDisplayHomeAsUpEnabled(true)
         }

         supportFragmentManager.addOnBackStackChangedListener {
             val hasBackStack =supportFragmentManager.backStackEntryCount > 0
             if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
             } else {
                 supportActionBar?.setDisplayHomeAsUpEnabled(false)
                 miniPlayerVisible()
                 onSupportNavigateUp()
             }
         }
    }

     override fun onSupportNavigateUp(): Boolean {
         supportFragmentManager.popBackStack()
         return super.onNavigateUp()
     }

     override fun onSongClicked(song: Song) {
         saveInstanceSong = song.title + " - " + song.artist
         miniplayertext.text = saveInstanceSong
         currentSong = song
     }

     private fun miniPLayerStuff() {
         miniPlayer.setOnClickListener {
             //val intent = Intent(this, MainActivity::class.java) //create intent
                 miniPlayerInvisible()
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

     private fun miniPlayerInvisible()  {
         miniPlayer.setVisibility(View.GONE)
         btnShuffle.setVisibility(View.GONE)
         miniplayertext.setVisibility(View.GONE)
     }

}
