package com.chotaling.ownlibrary.ui.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.ui.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView

class BookListFragment : BaseFragment<BookListViewModel>() {

    override val rootLayoutId: Int
        get() = R.layout.fragment_dashboard

    private lateinit var recycler_view : RecyclerView
    private lateinit var add_book_button : FloatingActionButton

    override fun setupUI() {
        recycler_view = rootView.findViewById(R.id.recycler_view)
        add_book_button = rootView.findViewById(R.id.add_book_button)

        add_book_button.setOnClickListener{
            this.findNavController().navigate(R.id.action_navigation_dashboard_to_addBookDialogFragment)
        }
    }

    override fun setupBindings() {
        ViewModel.bookList.observe(viewLifecycleOwner,  {
            if (!it.isEmpty())
            {
                recycler_view.adapter = BookListAdapter(it.toList())
            }
        })
    }
    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java);
    }

    class BookListAdapter(private val books : List<Book>) : RecyclerView.Adapter<BookListAdapter.BookCellViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookCellViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_book, viewGroup, false)
            return BookCellViewHolder(view)
        }

        override fun onBindViewHolder(holder: BookCellViewHolder, position: Int) {

            val book = books[position]
            holder.titleTextView.text = book.title
            holder.authorTextView.text = book.author
            holder.loadImageView(book.imageUrl)
        }

        override fun getItemCount(): Int {
            return books.size
        }

        class BookCellViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
            var imageView : ImageView
            var authorTextView : MaterialTextView
            var titleTextView : MaterialTextView

            init {
                imageView = view.findViewById(R.id.book_image_view)
                authorTextView = view.findViewById(R.id.author_description_text_view)
                titleTextView = view.findViewById(R.id.title_description_text_view)
            }

            fun loadImageView(url : String)
            {
                // Instantiate the RequestQueue.
                val queue = Volley.newRequestQueue(view.context)
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        //TODO: parse into image
                    },
                    {
                        imageView.setImageResource(R.drawable.ic_launcher_background)
                    })
                queue.add(stringRequest)
            }
        }

    }
}
