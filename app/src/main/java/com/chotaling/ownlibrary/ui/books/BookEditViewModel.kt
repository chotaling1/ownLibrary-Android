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
import org.bson.types.ObjectId

class BookEditViewModel : BaseViewModel()  {

    private val _bookService : BookService = BookService()
    private val _locationService : LocationService = LocationService()
    val locationList : MutableLiveData<Set<Location>> by lazy {
        MutableLiveData<Set<Location>>()
    }

    val editMode = MutableLiveData<Boolean>().apply {
        value = false
    }

    private var book : GoogleBookDto? = null

    val titleName = MutableLiveData<String?>().apply {
        value = ""
    }

    val authorName = MutableLiveData<String?>().apply {
        value = ""
    }

    val publisherName = MutableLiveData<String?>().apply {
        value = ""
    }

    val isbn = MutableLiveData<String?>().apply {
        value = ""
    }


    val notes = MutableLiveData<String?>().apply {
        value = ""
    }

    private var existingBookId : ObjectId? = null;

    val selectedLocation : MutableLiveData<Location> by lazy {
        MutableLiveData<Location>()
    }

    val selectedShelf : MutableLiveData<Shelf> by lazy {
        MutableLiveData<Shelf>()
    }

    override fun viewAppearing(arguments: Bundle?) {
        super.viewAppearing(arguments)
        getLocations()
        if (arguments != null )
        {
            if (arguments.containsKey("Book"))
            {
                book = arguments.get("Book") as GoogleBookDto
                titleName.value = book!!.volumeInfo.title;
                var authors = "";
                book!!.volumeInfo.authors.forEach { a -> authors += "$a " }
                authorName.value = authors;
                publisherName.value = book!!.volumeInfo.publisher
            }
            else if (arguments.containsKey("BookId"))
            {
                existingBookId = arguments.get("BookId") as ObjectId
                editMode.value = true;
                val existingBook = _bookService.getBookById(existingBookId!!)
                titleName.value = existingBook?.title
                authorName.value = existingBook?.author
                publisherName.value = existingBook?.publisher
                if (existingBook?.shelf != null)
                {
                    selectedLocation.value = _locationService.getLocationById(existingBook.shelf!!.locationId!!)
                    selectedShelf.value = existingBook.shelf;
                }
            }

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

    fun setBook()
    {
        if (editMode.value!!)
        {
            updateBook()
        }
        else
        {
            addBookToLibrary()
        }
    }

    private fun updateBook()
    {
        val realmBook = _bookService.getBooksByTitle(titleName.value!!)?.first()
        if (realmBook != null)
        {
            realmBook?.title = titleName.value!!
            realmBook?.author = authorName.value!!
            realmBook?.publisher = publisherName.value!!
            realmBook?.shelf = selectedShelf.value
            realmBook?.notes = notes.value
            _bookService.updateBook(realmBook);

        }


    }

    private fun addBookToLibrary()
    {
        val realmBook = Book()
        realmBook.author = authorName.value
        realmBook.title = titleName.value!!
        realmBook.imageUrl = if (book!!.volumeInfo.imageLinks?.smallThumbnail != null) book!!.volumeInfo.imageLinks?.smallThumbnail else ""
        realmBook.publisher = publisherName.value
        realmBook.shelf =  selectedShelf.value
        realmBook.notes = notes.value
        _bookService.addBook(realmBook)
    }
}