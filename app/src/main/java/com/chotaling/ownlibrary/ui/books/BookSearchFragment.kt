package com.chotaling.ownlibrary.ui.books

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.ui.BaseFragment

class BookSearchFragment : BaseFragment<BookSearchViewModel>(), SearchView.OnQueryTextListener {
    override val rootLayoutId: Int
        get() = R.layout.fragment_book_search

    @BindView(R.id.recycler_view) lateinit var recycler_view : RecyclerView
    @BindView(R.id.search_view) lateinit var search_view : SearchView

    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookSearchViewModel::class.java)
    }

    override fun setupBindings() {
        super.setupBindings()
    }

    override fun setupUI() {
        super.setupUI()

        search_view.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("onQueryTextSubmit", query!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("onQueryTextChange", newText!!)
        return true
    }
}