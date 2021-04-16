package com.chotaling.ownlibrary.ui.locations

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.ui.BaseDialogFragment
import com.chotaling.ownlibrary.ui.BaseViewModel
import com.chotaling.ownlibrary.ui.listadapters.LocationAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class AddShelfDialogFragment : BaseDialogFragment<AddShelfViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_dialog_shelf_add

    @BindView(R.id.shelf_name_field) lateinit var shelf_name_field : TextInputLayout
    @BindView(R.id.location_field) lateinit var location_field : TextInputLayout
    @BindView(R.id.button_cancel) lateinit var button_cancel : MaterialButton
    @BindView(R.id.button_add) lateinit var button_add : MaterialButton



    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(AddShelfViewModel::class.java)
    }

    override fun setupBindings() {
        ViewModel.locations.observe(viewLifecycleOwner,  {
            if (!it.isEmpty())
            {
                val adapter = LocationAdapter(requireContext(), android.R.layout.simple_list_item_1, it.toList())
                (location_field.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
        })
    }

    override fun setupUI() {

        shelf_name_field.editText?.doOnTextChanged { text, start, before, count ->
            ViewModel.shelfName.value = text.toString()
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


        button_cancel.setOnClickListener{
            dismiss()
        }

        button_add.setOnClickListener{
            ViewModel.addShelf()
            dismiss()
        }
    }




}