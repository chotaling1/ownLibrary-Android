package com.chotaling.ownlibrary.ui.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.ui.BaseFragment
import com.chotaling.ownlibrary.ui.books.viewholders.BookCellViewHolder

class BookLookupResultsFragment : BaseFragment<BookLookupResultsViewModel>() {

    override val rootLayoutId: Int
        get() = R.layout.fragment_book_lookup_results

    @BindView(R.id.recycler_view) lateinit var recycler_view : RecyclerView

    var bookDataSet = listOf<GoogleBookDto>()
    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookLookupResultsViewModel::class.java);
    }

    override fun setupUI() {
        recycler_view.layoutManager = LinearLayoutManager(context)

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    ViewModel.lookupBookAsync(ViewModel.currentIndex)
                }
            }
        })
    }

    override fun setupBindings() {
        ViewModel.resultsList.observe(viewLifecycleOwner,  {
            if (!it.isEmpty())
            {
                bookDataSet = it
                if (recycler_view.adapter == null)
                {
                    recycler_view.adapter = BookSearchResultAdapter(bookDataSet, ViewModel)
                }
                else
                {
                    recycler_view.adapter!!.notifyDataSetChanged()
                }

            }
        })
    }

    class BookSearchResultAdapter(private val books : List<GoogleBookDto>,
                                    private val ViewModel : BookLookupResultsViewModel) : RecyclerView.Adapter<BookCellViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookCellViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_book, viewGroup, false)

            var viewHolder = BookCellViewHolder(view)
            viewHolder.extendedViewsVisible = false
            viewHolder.more_options_image.visibility = View.GONE
            return viewHolder
        }

        override fun onBindViewHolder(holder: BookCellViewHolder, position: Int) {
            val book = books[position]
            holder.titleTextView .text = book.volumeInfo.title

            var authors = ""
            book.volumeInfo.authors?.forEach { s ->
                if (!book.volumeInfo.authors.last().equals(s))
                    authors += "$s, "
                else
                    authors += "$s"
            }
            holder.authorTextView.text = authors
            if (book.volumeInfo.imageLinks?.smallThumbnail != null)
            {
                holder.loadImageView(book.volumeInfo.imageLinks?.smallThumbnail!!)
            }

            holder.view.setOnClickListener{
                val bundle = bundleOf("Book" to book)
                holder.view.findNavController().navigate(R.id.action_book_search_results_fragment_to_add_book_fragment, bundle)
            }
        }

        override fun getItemCount(): Int {
            return books.size
        }


    }
}