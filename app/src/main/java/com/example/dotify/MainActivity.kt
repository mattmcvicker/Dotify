package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var randomNumber = Random.nextInt(1000, 9999999);
    var newUserName = "";

    companion object {
        const val ARTIST_KEY = "ARTIST_KEY"
        const val SONG_KEY = "SONG_KEY"
        const val LARGE_ID = "LARGE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        editUser.visibility = View.INVISIBLE;
        btnApply.visibility = View.GONE;
        btnApply.visibility = View.INVISIBLE;

    }

    private fun randomGenerator() {
        val randomPlays = findViewById<TextView>(R.id.numPlays);
        numPlays.text = "$randomNumber Plays";
    }

    fun applyClicked(view: View) {
        val editUserName = findViewById<EditText>(R.id.editUser);
        newUserName = editUserName.getText().toString();
        if(newUserName != "") {
            editUser.visibility = View.INVISIBLE;
            username.visibility = View.VISIBLE;
            username.text = newUserName;
            btnchangeUser.visibility = View.VISIBLE;
            username.text = newUserName;
            btnApply.visibility = View.GONE;
            btnApply.visibility = View.INVISIBLE;
        }
    }

    fun changeClicked(view: View) {
            editUser.visibility = View.VISIBLE;
            username.visibility = View.INVISIBLE;
            btnchangeUser.visibility = View.GONE;
            btnchangeUser.visibility = View.INVISIBLE;
            btnApply.visibility = View.VISIBLE;
    }

}

