package com.chotaling.ownlibrary.ui.books

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.services.BookService
import com.chotaling.ownlibrary.ui.BaseViewModel
import io.realm.Case
import io.realm.RealmResults

class BookSearchViewModel : BaseViewModel() {

    private val bookService : BookService = BookService()

    private var realmBooks : RealmResults<Book>? = null
    val bookList : MutableLiveData<Set<Book>> by lazy {
        MutableLiveData<Set<Book>>()
    }

    var searchType : SearchType = SearchType.Title

    override fun viewAppearing(arguments: Bundle?) {
        super.viewAppearing(arguments)
        realmBooks = bookService.getAllBooks();
        bookList.value = realmBooks?.toSet()
        realmBooks?.addChangeListener { allBooks ->
            bookList.value = allBooks.toSet()
        }
    }

    fun searchParametersUpdate(search : String)
    {
        if (realmBooks != null)
        {
            when (searchType)
            {
                SearchType.Author -> {
                    bookList.value = realmBooks!!.where().contains("author", search, Case.INSENSITIVE).findAll().toSet()
                }

                SearchType.Publisher -> {
                    bookList.value = realmBooks!!.where().contains("publisher", search, Case.INSENSITIVE).findAll().toSet()
                }

                else -> {
                    bookList.value = realmBooks!!.where().contains("title", search, Case.INSENSITIVE).findAll().toSet()
                }
            }
        }
    }

    enum class SearchType(value : Int) {
        Title(0),
        Author(1),
        Shelf(2),
        Publisher(3);
    }
}