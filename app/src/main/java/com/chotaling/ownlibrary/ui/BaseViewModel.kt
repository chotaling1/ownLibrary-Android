package com.chotaling.ownlibrary.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    open fun viewAppearing(arguments : Bundle?) {}
    open fun viewDisappearing() {}
    open fun viewCreated() {}
    open fun viewDestroyed() {}
}