package com.chotaling.ownlibrary.infrastructure.repositories

import android.app.Application
import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.chotaling.ownlibrary.OwnLibraryApplication

class BaseRepository(private val context : Context) {

    open fun getData(url : String)
    {

        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            { response ->

            },
            {  });
    }
}