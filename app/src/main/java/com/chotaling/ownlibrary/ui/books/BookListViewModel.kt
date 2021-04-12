package com.chotaling.ownlibrary.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.services.BookService

class BookListViewModel : ViewModel() {

    private val bookService : BookService = BookService()
    private val _bookList = MutableLiveData<Set<Book>>().apply {
        value = getBookList()
    }
    val bookList : LiveData<Set<Book>> = _bookList

    fun getBookList() : Set<Book>?
    {
        return bookService.getAllBooks();
    }
}