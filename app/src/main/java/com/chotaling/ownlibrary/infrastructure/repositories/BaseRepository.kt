package com.chotaling.ownlibrary.infrastructure.repositories

import android.app.Application
import android.content.Context
import android.os.Debug
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.chotaling.ownlibrary.OwnLibraryApplication
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookResultDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.Console
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutionException
import kotlin.math.log

open class BaseRepository() {

    suspend inline fun <reified T> getData(urlString : String) : T?
    {

        val result = withContext(Dispatchers.IO) {
            val inputStream: InputStream


            // create URL
            val url: URL = URL(urlString)
            // create HttpURLConnection
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

            // make GET request to the given URL
            conn.connect()

            // receive response as inputStream
            inputStream = conn.inputStream

            if (inputStream != null) {
                val bufferedReader: BufferedReader? = BufferedReader(InputStreamReader(inputStream))

                var line:String? = bufferedReader?.readLine()
                var result:String = ""

                while (line != null) {
                    result += line
                    line = bufferedReader?.readLine()
                }

                inputStream.close()
                result
            }
            else
                ""


        }
        Log.d(BaseRepository::javaClass.toString(), result)
        val gson = Gson()
        return gson.fromJson(result, T::class.java);
    }
}