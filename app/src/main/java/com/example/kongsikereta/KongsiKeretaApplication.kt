package com.example.kongsikereta

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KongsiKeretaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}