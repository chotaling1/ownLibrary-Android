package com.chotaling.ownlibrary.ui.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chotaling.ownlibrary.domain.services.BookService

class AddBookViewModel : ViewModel() {

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

    fun lookupBook() {

        _bookService.lookupBook(isbn.value.toString(), author.value.toString(), title.value.toString())
    }
}