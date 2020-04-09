package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var randomNumber = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        randomGenerator()
    }

    private fun randomGenerator() {
        randomNumber = Random.nextInt(1000, 9999999);
        val randomPlays = findViewById<TextView>(R.id.numPlays);
        numPlays.text = "$randomNumber Plays";
    }


    fun changeClicked(view: View) {
        Log.i("mattmcv", "You have clicked");

    }

    
  //  fun trackNextClicked() {
    //    Toast.makeText(this, "Next song", Toast.LENGTH_SHORT)
    //}
}
