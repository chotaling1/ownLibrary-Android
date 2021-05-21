package com.chotaling.ownlibrary.ui.books.viewholders

import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.interfaces.ISearchCallback
import com.google.android.material.textfield.TextInputLayout

class BookListSearchViewHolder(val view : View, val iSearchCallback : ISearchCallback) : RecyclerView.ViewHolder(view) {
    @BindView(R.id.search_field) lateinit var search_field : TextInputLayout

    init {
        ButterKnife.bind(this, view)
        search_field.editText?.doOnTextChanged{ text, start, before, count ->
            iSearchCallback.onSearchUpdated(text.toString())
        }
    }
}