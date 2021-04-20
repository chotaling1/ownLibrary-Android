package com.chotaling.ownlibrary.ui

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {


    fun setContext(context : Context) {
        _context = context;
    }

    protected var _context : Context? = null
    open fun viewAppearing(arguments : Bundle?) {}
    open fun viewDisappearing() {}
    open fun viewCreated() {}
    open fun viewDestroyed() {}
}