package com.chotaling.ownlibrary.ui.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.services.BookService
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto

class BookSearchResultsViewModel : ViewModel() {

    private val bookService : BookService = BookService()
    val resultsList : MutableLiveData<Array<GoogleBookDto>> by lazy {
        MutableLiveData<Array<GoogleBookDto>>()
    }

    fun addBookToLibrary(book : GoogleBookDto)
    {
        val realmBook = Book()
        realmBook.author = book.volumeInfo.authors?.first()
        realmBook.title = book.volumeInfo.title
        realmBook.imageUrl = book.volumeInfo.imageLinks?.smallThumbnail
        realmBook.publisher = book.volumeInfo.publisher
        bookService.addBook(realmBook)
    }
}