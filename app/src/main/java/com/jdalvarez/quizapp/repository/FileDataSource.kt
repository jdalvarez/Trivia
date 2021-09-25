package com.jdalvarez.quizapp.repository

import android.content.Context
import java.io.IOException

class FileDataSource (val context:Context){
    fun loadFileFromAssets(filename: String): String?{
        var json: String? = null
        try {
            val inputS = context.assets.open(filename)
            val size = inputS.available()
            val buffer = ByteArray(size)
            inputS.read(buffer)
            inputS.close()
            json = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return json
    }

}


