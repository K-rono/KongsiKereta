package com.example.kongsikereta.util

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("user_SharedPreferences",Context.MODE_PRIVATE)
        }


    }
}