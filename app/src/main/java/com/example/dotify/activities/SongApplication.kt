package com.example.dotify.activities

import android.app.Application
import android.util.Log

class SongApplication: Application() {
    override fun onCreate() { //callback of the oncreate (about to launch the app)
        super.onCreate()
        Log.i("Hello", "application is booting up")
    }
}