package com.example.dotify

import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song

class SongDiffCallback(
    private val oldSong: List<Song>,
    private val newSong: List<Song>
): DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val one = oldSong[oldItemPosition]
        val two = newSong[newItemPosition]

        return one.id == two.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val one = oldSong[oldItemPosition]
        val two = newSong[newItemPosition]

        return one.id == two.id
    }

    override fun getOldListSize(): Int = oldSong.size


    override fun getNewListSize(): Int = newSong.size


}