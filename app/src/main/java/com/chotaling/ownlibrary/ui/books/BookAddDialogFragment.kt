package com.chotaling.ownlibrary.ui.books

import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.models.Shelf
import com.chotaling.ownlibrary.ui.BaseDialogFragment
import com.chotaling.ownlibrary.ui.listadapters.LocationAdapter
import com.chotaling.ownlibrary.ui.listadapters.ShelfAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class BookAddDialogFragment : BaseDialogFragment<BookAddViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_dialog_book_add

    @BindView(R.id.location_field) lateinit var location_field : TextInputLayout
    @BindView(R.id.shelf_field) lateinit var shelf_field : TextInputLayout
    @BindView(R.id.button_cancel) lateinit var button_cancel : MaterialButton
    @BindView(R.id.button_add) lateinit var button_add : MaterialButton

    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookAddViewModel::class.java);
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
            findNavController().navigate(R.id.action_add_book_fragment_to_navigation_book_list)
            dismiss()
        }

        button_cancel.setOnClickListener {
            dismiss()
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
    }
}