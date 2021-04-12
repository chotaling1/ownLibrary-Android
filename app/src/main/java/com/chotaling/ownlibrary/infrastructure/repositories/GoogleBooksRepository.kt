package com.chotaling.ownlibrary.infrastructure.repositories

import com.chotaling.ownlibrary.BuildConfig
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookResultDto
import com.google.gson.Gson
import java.net.URLEncoder


class GoogleBooksRepository : BaseRepository() {

    private val baseURL : String = "https://www.googleapis.com/books/v1/volumes?q="
    suspend fun lookupBookByIsbn(isbn : String) : Array<GoogleBookDto>? {
        val query = URLEncoder.encode(String.format("isbn=%s", isbn))
        val url = baseURL + query + "&key=" + BuildConfig.GOOGLE_API_KEY
        var data = getData(url)
        val gson = Gson()
        var typedObject = gson.fromJson(data, GoogleBookResultDto::class.java)
        return typedObject.items
    }



}