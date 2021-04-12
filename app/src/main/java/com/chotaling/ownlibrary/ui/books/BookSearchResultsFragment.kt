package com.chotaling.ownlibrary.ui.books

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.ui.BaseFragment
import com.google.android.material.textview.MaterialTextView

class BookSearchResultsFragment : BaseFragment<BookSearchResultsViewModel>() {

    override val rootLayoutId: Int
        get() = R.layout.fragment_book_search_results

    private lateinit var recycler_view : RecyclerView

    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookSearchResultsViewModel::class.java);
        ViewModel.resultsList.value = arguments?.get("BookResults") as Array<GoogleBookDto>
    }

    override fun setupUI() {
        recycler_view = rootView.findViewById(R.id.recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(context)
    }

    override fun setupBindings() {
        ViewModel.resultsList.observe(viewLifecycleOwner,  {
            if (!it.isEmpty())
            {
                recycler_view.adapter = BookSearchResultAdapter(it.toList(), ViewModel)
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
                    _viewModel.addBookToLibrary(_book)
                    view.findNavController().navigate(R.id.navigation_book_list)
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