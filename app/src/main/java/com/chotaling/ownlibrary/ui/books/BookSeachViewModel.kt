package com.chotaling.ownlibrary.ui.books

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chotaling.ownlibrary.domain.services.BookService
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto

class BookSeachViewModel : ViewModel() {

    private val _bookService = BookService()
    val author : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    val isbn : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val title : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    suspend fun lookupBook() : Array<GoogleBookDto>? {

        if (isbn.value != null)
        {
            return _bookService.lookupBook(isbn.value!!, "", "")
        }

        return null;

    }
}