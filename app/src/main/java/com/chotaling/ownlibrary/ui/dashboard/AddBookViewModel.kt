package com.chotaling.ownlibrary.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddBookViewModel : ViewModel() {
    private val _author = MutableLiveData<String>().apply {
        value = ""
    }
    val author: LiveData<String> = _author

    private val _isbn = MutableLiveData<String>().apply {
        value = ""
    }
    val isbn: LiveData<String> = _isbn

    private val _title = MutableLiveData<String>().apply {
        value = ""
    }
    val title: LiveData<String> = _title

}