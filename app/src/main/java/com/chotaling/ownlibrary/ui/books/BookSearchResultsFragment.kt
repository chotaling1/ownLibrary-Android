package com.chotaling.ownlibrary.ui.books

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.ui.BaseFragment
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.launch

class BookSearchResultsFragment : BaseFragment<BookSearchResultsViewModel>() {

    override val rootLayoutId: Int
        get() = R.layout.fragment_book_search_results

    @BindView(R.id.recycler_view) lateinit var recycler_view : RecyclerView

    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookSearchResultsViewModel::class.java);
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
                recycler_view.adapter = BookSearchResultAdapter(it.toList(), ViewModel)
                if (ViewModel.currentIndex > 0)
                {
                    recycler_view.scrollToPosition(ViewModel.currentIndex - ViewModel.TAKE_CONSTANT - 2)
                }

            }
        })
    }

    class BookSearchResultAdapter(private val books : List<GoogleBookDto>,
                                    private val ViewModel : BookSearchResultsViewModel) : RecyclerView.Adapter<BookCellViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookCellViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_book, viewGroup, false)
            return BookCellViewHolder(view)
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
            book.volumeInfo.imageLinks?.smallThumbnail?.let {holder.loadImageView(it)}
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