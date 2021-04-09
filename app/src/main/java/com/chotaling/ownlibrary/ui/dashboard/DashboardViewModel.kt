package com.chotaling.ownlibrary.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chotaling.ownlibrary.models.Book
import com.chotaling.ownlibrary.services.BookService

class DashboardViewModel : ViewModel() {

    private val bookService : BookService = BookService()
    private val _bookList = MutableLiveData<Set<Book>>().apply {
        value = null
    }
    val bookList : LiveData<Set<Book>> = _bookList

    fun getBookList()
    {
        _bookList.value = bookService.getAllBooks();
    }
}