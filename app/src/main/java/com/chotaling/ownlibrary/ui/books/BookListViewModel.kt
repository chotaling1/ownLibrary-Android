package com.chotaling.ownlibrary.ui.books

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chotaling.ownlibrary.domain.RealmConfig
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.services.BookService
import com.chotaling.ownlibrary.ui.BaseViewModel

class BookListViewModel : BaseViewModel() {

    private val bookService : BookService = BookService()
    val bookList : MutableLiveData<Set<Book>> by lazy {
        MutableLiveData<Set<Book>>()
    }

    override fun viewAppearing(arguments: Bundle?) {
        super.viewAppearing(arguments)
        getBookList()
    }
    fun getBookList()
    {
        val books = bookService.getAllBooks();
        bookList.value = books?.toSet()
        books?.addChangeListener { allBooks ->
            bookList.value = allBooks.toSet()
        }
    }
}