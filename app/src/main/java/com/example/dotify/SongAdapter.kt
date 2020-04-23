package com.example.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongAdapter(listOfSongs: List<Song>): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var listOfSongs : List<Song> = listOfSongs.toList()

    var onSongClickListener: ((item: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return SongViewHolder(itemView)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val songsName = listOfSongs[position]
        holder.bind(songsName)
    }

    ///////////////////////////////////////////////////////////////
    fun change(newSongs: List<Song>) {
        val callback = SongDiffCallback(listOfSongs, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

        listOfSongs = newSongs
    }
    ////////////////////////////////////////////////////////////////

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val songName by lazy{ itemView.findViewById<TextView>(R.id.songName) }
        private val artistName by lazy{ itemView.findViewById<TextView>(R.id.artistName) }
        private val albumImage by lazy{ itemView.findViewById<ImageView>(R.id.albumImage) }
        fun bind(item: Song) {
            songName.text = item.title
            artistName.text = item.artist
            albumImage.setImageResource(item.smallImageID)

            itemView.setOnClickListener {
                onSongClickListener?.invoke(item)
            }
        }
    }

}

