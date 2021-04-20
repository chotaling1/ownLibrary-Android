package com.chotaling.ownlibrary.ui.books

import android.content.DialogInterface
import android.graphics.Bitmap
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import butterknife.BindView
import butterknife.ButterKnife
import com.android.volley.Request
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.services.LocationService
import com.chotaling.ownlibrary.ui.BaseFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.transition.MaterialContainerTransform

class BookListFragment : BaseFragment<BookListViewModel>() {

    override val rootLayoutId: Int
        get() = R.layout.fragment_book_list

    @BindView(R.id.recycler_view) lateinit var recycler_view : RecyclerView
    @BindView(R.id.add_book_button) lateinit var add_book_button : FloatingActionButton

    override fun setupUI() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        add_book_button.setOnClickListener{
            this.findNavController().navigate(R.id.action_navigation_dashboard_to_addBookDialogFragment)
        }
    }

    override fun setupBindings() {
        ViewModel.bookList.observe(viewLifecycleOwner,  {
            if (!it.isEmpty())
            {
                recycler_view.adapter = BookListAdapter(it.toList(), this)
            }
        })
    }
    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java);
    }


    class BookListAdapter(private val books : List<Book>, private val owner : BookListFragment) : RecyclerView.Adapter<BookCellViewHolder>() {

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
                //holder.view.findNavController().navigate(R.id.action_navigation_locations_to_add_location_fragment, bundle)
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
            return books.size
        }
    }
}
