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
    }

    override fun setupBindings() {
        ViewModel.resultsList.observe(viewLifecycleOwner,  {
            if (!it.isEmpty())
            {
                recycler_view.adapter = BookSearchResultAdapter(it.toList(), ViewModel)
            }
        })

        ViewModel.barcodeResult.observe(viewLifecycleOwner, {
            if (it != null)
            {
                val progressDialog = ProgressDialog(context)
                progressDialog.show()
                lifecycleScope.launch {
                    ViewModel.lookupBook()
                    progressDialog.dismiss()
                }
            }
        })
    }

    class BookSearchResultAdapter(private val books : List<GoogleBookDto>,
                                    private val ViewModel : BookSearchResultsViewModel) : RecyclerView.Adapter<BookSearchResultAdapter.BookCellViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookCellViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_book, viewGroup, false)
            return BookCellViewHolder(view)
        }

        override fun onBindViewHolder(holder: BookCellViewHolder, position: Int) {
            val book = books[position]
            holder.bindData(book, ViewModel)
        }

        override fun getItemCount(): Int {
            return books.size
        }

        class BookCellViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            private lateinit var _book : GoogleBookDto
            private lateinit var _viewModel: BookSearchResultsViewModel
            var imageView: ImageView
            var authorTextView: MaterialTextView
            var titleTextView: MaterialTextView

            init {
                imageView = view.findViewById(R.id.book_image_view)
                authorTextView = view.findViewById(R.id.author_description_text_view)
                titleTextView = view.findViewById(R.id.title_description_text_view)

                view.setOnClickListener{
                    val bundle = bundleOf("Book" to _book)
                    view.findNavController().navigate(R.id.action_book_search_results_fragment_to_add_book_fragment, bundle)
                }

            }

            fun bindData(book : GoogleBookDto,
                        viewModel : BookSearchResultsViewModel)
            {
                _viewModel = viewModel
                _book = book
                titleTextView.text = book.volumeInfo.title

                var authors = ""
                book.volumeInfo.authors?.forEach { s ->
                    if (!book.volumeInfo.authors.last().equals(s))
                        authors += "$s, "
                    else
                        authors += "$s"
                }
                authorTextView.text = authors
                book.volumeInfo.imageLinks?.smallThumbnail?.let {loadImageView(it)}
            }



            fun loadImageView(url: String) {

                val queue = Volley.newRequestQueue(view.context)
                val imageRequest = ImageRequest(url,
                    { response ->
                        imageView.setImageBitmap(response)
                    },
                    0,
                    0,
                    ImageView.ScaleType.CENTER,
                    Bitmap.Config.RGB_565,
                    {
                        imageView.setImageResource(R.drawable.ic_launcher_background)
                    })

                queue.add(imageRequest)
            }
        }
    }
}