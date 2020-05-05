package com.example.dotify.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.R
import com.example.dotify.SongAdapter
import com.example.dotify.fragments.SongListFragment.Companion.currentSong
import kotlinx.android.synthetic.main.activity_main_main.*
import kotlinx.android.synthetic.main.activity_song_list.*
import kotlinx.android.synthetic.main.activity_song_list_fragment.*
import kotlinx.android.synthetic.main.activity_song_list_fragment.songList
import java.util.*


class SongListFragment: Fragment() {
    private lateinit var songAdapter : SongAdapter


    private var listOfSongs : List<Song>? = null

    private var onSongClickListener: OnSongClickListener? = null

    companion object {
        const val KEEP_ORDER = "LIstorder"
        const val KEEP_SHUFFLE = "shuffle"
        val TAG: String = SongListFragment::class.java.simpleName

        var currentSong = SongDataProvider.createRandomSong()

        const val SONG_LIST = "SongList"

        fun newInstance(SongList: ArrayList<Song>): SongListFragment {
            val args = Bundle().apply {
                putParcelableArrayList(SONG_LIST, SongList)
            }

            return SongListFragment().apply {
                setArguments(args)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(KEEP_ORDER, listOfSongs?.toList()?.let { ArrayList(it) })
        super.onSaveInstanceState(outState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) { //for data before UI is shown
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) { //
            with(savedInstanceState) {
                listOfSongs = getParcelableArrayList(KEEP_ORDER)
            }
        } else {
            listOfSongs = null
        }

        var oldSongs = getArguments()?.getParcelableArrayList<Song>(SONG_LIST)
        listOfSongs= oldSongs?.toList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_song_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songAdapter = listOfSongs?.let { SongAdapter(it) }!!
        shuffleList(songAdapter)

            songAdapter.onSongClickListener = { item ->
                onSongClickListener?.onSongClicked(item)
                currentSong = item
        }
        songList.adapter = songAdapter
    }

     fun shuffleList(songAdapter: SongAdapter?) {
         val btnShuffle = activity!!.findViewById<View>(R.id.btnShuffle) as Button
        btnShuffle.setOnClickListener{
            val newSongs = listOfSongs?.toMutableList()?.apply {
                shuffle()
            }?.toMutableList()
            if (songAdapter != null) {
                if (newSongs != null) {
                    songAdapter.change(newSongs)
                }
            }
        }
    }

}

interface OnSongClickListener {
    fun onSongClicked(song: Song)

}

