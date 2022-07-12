package com.chotaling.ownlibrary.ui.books

import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.ui.BaseFragment
import com.chotaling.ownlibrary.ui.books.viewholders.BookCellViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout

class BookSearchFragment : BaseFragment<BookSearchViewModel>(), SearchView.OnQueryTextListener, TabLayout.OnTabSelectedListener {
    override val rootLayoutId: Int
        get() = R.layout.fragment_book_search

    @BindView(R.id.recycler_view) lateinit var recycler_view : RecyclerView
    @BindView(R.id.search_view) lateinit var search_view : SearchView
    @BindView(R.id.search_tab_layout) lateinit var search_tab_layout : TabLayout
    @BindView(R.id.add_book_button) lateinit var add_book_button : FloatingActionButton
    lateinit var bookListDataSet : List<Book>
    var bookListAdapter : BookListAdapter? = null
    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookSearchViewModel::class.java)
    }

    override fun setupBindings() {
        ViewModel.bookList.observe(viewLifecycleOwner,  {
            bookListDataSet = it.toList()
            if (bookListAdapter == null)
            {
                bookListAdapter = BookListAdapter(bookListDataSet, this)
                recycler_view.adapter = bookListAdapter
            }
            else
            {
                bookListAdapter?.updateData(bookListDataSet)
            }
        })

    }

    override fun setupUI() {
        super.setupUI()
        recycler_view.layoutManager = LinearLayoutManager(context)
        search_view.setOnQueryTextListener(this)
        search_tab_layout.addOnTabSelectedListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("onQueryTextSubmit", query!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("onQueryTextChange", newText!!)
        if (newText != null && !newText!!.isEmpty())
        {
            ViewModel.searchParametersUpdate(newText)
        }

        return true
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null)
        {
            search_view.setQuery("", false)
            ViewModel.searchType = BookSearchViewModel.SearchType.values()[tab.position]

        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    class BookListAdapter(private var books : List<Book>, private val owner : BookSearchFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        enum class ViewType (val value : Int) {
            BookViewHolder(1)
        }

        fun updateData(updatedBooks : List<Book>)
        {
            books = updatedBooks
            notifyDataSetChanged()
        }
        override fun getItemViewType(position: Int): Int {
            return ViewType.BookViewHolder.value
        }
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            // Create a new view, which defines the UI of the list item

            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_book, viewGroup, false)
            var holder = BookCellViewHolder(view)
            holder.extendedViewsVisible = false
            return holder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if (holder is BookCellViewHolder)
            {

                bindBookCellViewHolder(holder, position)
            }
        }

        private fun bindBookCellViewHolder(holder : BookCellViewHolder, position : Int)
        {
            val book = books[position]
            holder.titleTextView.text = book.title
            holder.authorTextView.text = book.author
            if (book.imageUrl != null)
            {
                holder.loadImageView(book.imageUrl!!)
            }

            holder.publisher_description_text_view.text = book.publisher

            if (book.shelf != null)
            {
                holder.shelf_text_view.text = "This book is on the ${book.shelf?.name} shelf"
            }
            else
            {
                holder.shelf_text_view.text = "This book does not belong to a shelf"
            }

            holder.view.setOnClickListener {
                //TODO: Navigate to edit book form
            }

        }

        override fun getItemCount(): Int {
            return books.size
        }
    }
}