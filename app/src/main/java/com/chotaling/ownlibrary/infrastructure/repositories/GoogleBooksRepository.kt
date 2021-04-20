package com.chotaling.ownlibrary.infrastructure.repositories

import com.chotaling.ownlibrary.BuildConfig
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookResultDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder


class GoogleBooksRepository : BaseRepository() {

    private val baseURL : String = "https://www.googleapis.com/books/v1/volumes?q="

    suspend fun lookupBook(isbn : String?, author : String?, title : String?, index : Int?, maxResults : Int?) : Array<GoogleBookDto>? {

        return withContext(Dispatchers.IO) {
            val queryList = mutableListOf<String>()
            if (isbn != null)
            {
                queryList.add("isbn=${URLEncoder.encode(isbn)}")
            }

            if (author != null)
            {
                queryList.add("inauthor:${URLEncoder.encode(author)}")
            }

            if (title != null)
            {
                queryList.add("intitle:${URLEncoder.encode(title)}")
            }

            if (index != null)
            {
                queryList.add("startIndex=${URLEncoder.encode(index.toString())}")
            }

            if (maxResults != null)
            {
                queryList.add("maxResults=${URLEncoder.encode(maxResults.toString())}")
            }

            var url = baseURL
            queryList.forEach { s ->
                url += "${s}&"
            }

            url += "key=" + BuildConfig.GOOGLE_API_KEY

            var data = getData<GoogleBookResultDto>(url)

            data?.items
        }

    }


}