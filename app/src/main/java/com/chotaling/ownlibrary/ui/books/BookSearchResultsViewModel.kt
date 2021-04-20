package com.chotaling.ownlibrary.ui.books

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Looper
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.services.BookService
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.ui.BaseViewModel
import kotlinx.coroutines.*

class BookSearchResultsViewModel : BaseViewModel() {

    private val _bookService : BookService = BookService()

    var currentIndex = 0;
    val TAKE_CONSTANT = 40
    val resultsList = MutableLiveData<MutableList<GoogleBookDto>>().apply  {
        value = mutableListOf()
    }

    val isbn : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val author : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val title : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }



    override fun viewAppearing(arguments: Bundle?) {
        if (arguments!!.containsKey("isbn"))
        {
            isbn.value = arguments.getString("isbn")
        }

        if (arguments!!.containsKey("author"))
        {
            author.value = arguments.getString("author")
        }

        if (arguments!!.containsKey("title"))
        {
            title.value = arguments.getString("title")
        }

        lookupBookAsync(0)
    }

    fun lookupBookAsync(index : Int)  {
        currentIndex = index
        val progressDialog = ProgressDialog(_context)
        progressDialog.show()
        viewModelScope.launch {
            val newBooks = _bookService.lookupBook(isbn.value, author.value, title.value, currentIndex, TAKE_CONSTANT)
            if (newBooks != null)
            {
                currentIndex += TAKE_CONSTANT
                var mutableList = resultsList.value;
                mutableList?.addAll(newBooks)
                resultsList.value = mutableList;
            }
            progressDialog.dismiss()
        }
    }
}