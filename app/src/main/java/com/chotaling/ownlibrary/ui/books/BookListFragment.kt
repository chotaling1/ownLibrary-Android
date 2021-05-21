package com.chotaling.ownlibrary.ui.books

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.interfaces.ISearchCallback
import com.chotaling.ownlibrary.ui.BaseFragment
import com.chotaling.ownlibrary.ui.MainActivity
import com.chotaling.ownlibrary.ui.books.viewholders.BookCellViewHolder
import com.chotaling.ownlibrary.ui.books.viewholders.BookListSearchViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BookListFragment : BaseFragment<BookListViewModel>(), ISearchCallback {

    override val rootLayoutId: Int
        get() = R.layout.fragment_book_list

    @BindView(R.id.recycler_view) lateinit var recycler_view : RecyclerView
    @BindView(R.id.add_book_button) lateinit var add_book_button : FloatingActionButton

    var bookListAdapter : BookListAdapter? = null
    lateinit var bookListDataSet : List<Book>
    override fun setupUI() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        add_book_button.setOnClickListener{
            this.findNavController().navigate(R.id.action_navigation_dashboard_to_addBookDialogFragment)
        }

//        val mainActivity = activity as MainActivity;
//        if (mainActivity != null)
//        {
//            mainActivity.toolbar.
//        }
    }

    override fun setupBindings() {
        ViewModel.bookList.observe(viewLifecycleOwner,  {
            if (!it.isEmpty())
            {
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

            }
        })
    }

    override fun onPause() {
        super.onPause()
        bookListAdapter = null
    }

    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java);
    }

    override fun onSearchUpdated(searchParam: String) {
        if (searchParam.isNotEmpty())
        {
            ViewModel.searchParametersUpdate(searchParam, BookListViewModel.SearchType.Title)
        }
    }

    class BookListAdapter(private var books : List<Book>, private val owner : BookListFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        enum class ViewType (val value : Int) {
            SearchViewHolder(0),
            BookViewHolder(1)
        }

        fun updateData(updatedBooks : List<Book>)
        {
            books = updatedBooks
            notifyDataSetChanged()
        }
        override fun getItemViewType(position: Int): Int {
            if (position == 0)
            {
                return ViewType.SearchViewHolder.value
            }

            return ViewType.BookViewHolder.value
        }
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            // Create a new view, which defines the UI of the list item

            if (viewType == ViewType.SearchViewHolder.value)
            {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.cell_search, viewGroup, false)
                var holder = BookListSearchViewHolder(view, owner)
                return holder
            }
            else
            {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.cell_book, viewGroup, false)
                var holder = BookCellViewHolder(view)
                holder.extendedViewsVisible = false
                return holder
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if (holder is BookCellViewHolder)
            {

                bindBookCellViewHolder(holder, position - 1)
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
                holder.toggleExtendedViews()
            }


            holder.edit_button.setOnClickListener{
                val bundle = bundleOf("BookId" to book.id)
                holder.view.findNavController().navigate(R.id.action_navigation_book_list_to_navigation_book_edit_fragment, bundle)
            }

            holder.delete_button.setOnClickListener{

                AlertDialog.Builder(owner.requireContext())
                    .setMessage(R.string.delete_confirmation)
                    .setPositiveButton(R.string.yes, object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            owner.ViewModel.deleteBook(book)
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .show()
            }
        }

        override fun getItemCount(): Int {
            return books.size + 1
        }
    }
}
