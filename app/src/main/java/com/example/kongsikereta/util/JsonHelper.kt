package com.example.kongsikereta.util

import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.io.FileWriter

object JsonHelper {
    //create
    fun <T> createJsonFile(context: Context,fileName: String, data: T): File {
        val gson = Gson()
        val json = gson.toJson(data)
        val file = File(context.cacheDir,fileName)
        val fileWriter = FileWriter(file)
        fileWriter.use {
            it.write(json)
        }
        return file
    }

    //parse

}