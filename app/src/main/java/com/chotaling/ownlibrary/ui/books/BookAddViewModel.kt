package com.chotaling.ownlibrary.ui.books

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.models.Shelf
import com.chotaling.ownlibrary.domain.services.BookService
import com.chotaling.ownlibrary.domain.services.LocationService
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.ui.BaseViewModel

class BookAddViewModel : BaseViewModel()  {

    private val _bookService : BookService = BookService()
    private val _locationService : LocationService = LocationService()
    val locationList : MutableLiveData<Set<Location>> by lazy {
        MutableLiveData<Set<Location>>()
    }

    private var book : GoogleBookDto? = null

    val selectedLocation : MutableLiveData<Location> by lazy {
        MutableLiveData<Location>()
    }

    val selectedShelf : MutableLiveData<Shelf> by lazy {
        MutableLiveData<Shelf>()
    }

    override fun viewAppearing(arguments: Bundle?) {
        super.viewAppearing(arguments)
        if (arguments != null && arguments.containsKey("Book"))
        {
            book = arguments.get("Book") as GoogleBookDto
            getLocations()
        }
    }

    fun getLocations()
    {
        val locations = _locationService.getAllLocations();
        locationList.value = locations?.toSet()
        locations?.addChangeListener { allBooks ->
            locationList.value = allBooks.toSet()
        }
    }

    fun addBookToLibrary()
    {
        val realmBook = Book()
        realmBook.author = book!!.volumeInfo.authors?.first()
        realmBook.title = book!!.volumeInfo.title
        realmBook.imageUrl = if (book!!.volumeInfo.imageLinks.smallThumbnail != null) book!!.volumeInfo.imageLinks.smallThumbnail else ""
        realmBook.publisher = if (book!!.volumeInfo.publisher != null) book!!.volumeInfo.publisher else ""
        realmBook.shelf =  selectedShelf.value
        _bookService.addBook(realmBook)
    }
}