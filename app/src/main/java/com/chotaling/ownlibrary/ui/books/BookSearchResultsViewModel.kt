package com.chotaling.ownlibrary.ui.books

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.services.BookService
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.ui.BaseViewModel

class BookSearchResultsViewModel : BaseViewModel() {

    private val _bookService : BookService = BookService()
    val resultsList : MutableLiveData<Array<GoogleBookDto>> by lazy {
        MutableLiveData<Array<GoogleBookDto>>()
    }

    val barcodeResult : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    override fun viewAppearing(arguments: Bundle?) {
        if (arguments!!.containsKey("BookResult"))
        {
            val bookresults = arguments.get("BookResult") as Array<GoogleBookDto>
            resultsList.value = bookresults
        }

        if (arguments.containsKey("BarcodeResult"))
        {
            val barcodeResultString = arguments.get("BarcodeResult") as String
            if (barcodeResultString != null)
            {
                barcodeResult.value = barcodeResultString
            }
        }
    }

    fun addBookToLibrary(book : GoogleBookDto)
    {
        val realmBook = Book()
        realmBook.author = book.volumeInfo.authors?.first()
        realmBook.title = book.volumeInfo.title
        realmBook.imageUrl = book.volumeInfo.imageLinks?.smallThumbnail
        realmBook.publisher = book.volumeInfo.publisher
        _bookService.addBook(realmBook)
    }

    suspend fun lookupBook() {

        if (barcodeResult.value != null)
        {
            resultsList.value = _bookService.lookupBook(barcodeResult.value!!, "", "")
        }


    }
}