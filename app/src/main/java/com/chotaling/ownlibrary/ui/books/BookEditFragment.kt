package com.chotaling.ownlibrary.ui.books

import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.models.Shelf
import com.chotaling.ownlibrary.ui.BaseDialogFragment
import com.chotaling.ownlibrary.ui.BaseFragment
import com.chotaling.ownlibrary.ui.listadapters.LocationAdapter
import com.chotaling.ownlibrary.ui.listadapters.ShelfAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class BookEditFragment : BaseFragment<BookEditViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_book

    @BindView(R.id.title_field) lateinit var title_field : TextInputLayout
    @BindView(R.id.author_field) lateinit var author_field : TextInputLayout
    @BindView(R.id.publisher_field) lateinit var publisher_field : TextInputLayout
    @BindView(R.id.location_field) lateinit var location_field : TextInputLayout
    @BindView(R.id.shelf_field) lateinit var shelf_field : TextInputLayout
    @BindView(R.id.notes_field) lateinit var notes_field : TextInputLayout
    @BindView(R.id.button_cancel) lateinit var button_cancel : MaterialButton
    @BindView(R.id.button_add) lateinit var button_add : MaterialButton

    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookEditViewModel::class.java);
    }

    override fun setupBindings() {
        ViewModel.locationList.observe(viewLifecycleOwner,  {
            if (!it.isEmpty())
            {
                val adapter = LocationAdapter(requireContext(), android.R.layout.simple_list_item_1, it.toList())
                (location_field.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
        })

        ViewModel.selectedLocation.observe(viewLifecycleOwner,  {
            if (it != null && !it.shelves.isEmpty())
            {
                val adapter = ShelfAdapter(requireContext(), android.R.layout.simple_list_item_1, it.shelves.toList())
                (shelf_field.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
        })
    }

    override fun setupUI() {

        button_add.setOnClickListener {
            ViewModel.addBookToLibrary()
            findNavController().navigate(R.id.action_navigation_book_edit_fragment_to_navigation_book_list)
        }

        button_cancel.setOnClickListener {
            findNavController().navigateUp()
        }

        (location_field.editText as? AutoCompleteTextView)?.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val location = parent?.adapter?.getItem(position) as? Location
                ViewModel.selectedLocation.value = location
            }
        }

        (shelf_field.editText as? AutoCompleteTextView)?.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val shelf = parent?.adapter?.getItem(position) as? Shelf
                ViewModel.selectedShelf.value = shelf
            }
        }

        title_field.editText?.doOnTextChanged { text, start, before, count ->
            ViewModel.titleName.value = text.toString()
        }

        author_field.editText?.doOnTextChanged { text, start, before, count ->
            ViewModel.authorName.value = text.toString()
        }

        publisher_field.editText?.doOnTextChanged { text, start, before, count ->
            ViewModel.publisherName.value = text.toString()
        }

        notes_field.editText?.doOnTextChanged { text, start, before, count ->
            ViewModel.notes.value = text.toString()
        }

    }
}