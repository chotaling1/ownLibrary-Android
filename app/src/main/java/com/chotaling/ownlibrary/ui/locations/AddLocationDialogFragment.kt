package com.chotaling.ownlibrary.ui.locations

import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.ui.BaseDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class AddLocationDialogFragment : BaseDialogFragment<AddLocationViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_dialog_location_add

    private lateinit var location_name_field : TextInputLayout
    private lateinit var address_field : TextInputLayout
    private lateinit var button_cancel : MaterialButton
    private lateinit var button_add : MaterialButton

    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(AddLocationViewModel::class.java)
    }

    override fun setupUI() {
        location_name_field = rootView.findViewById(R.id.location_name_field)
        address_field = rootView.findViewById(R.id.address_field)
        button_cancel = rootView.findViewById(R.id.button_cancel)
        button_add = rootView.findViewById(R.id.button_add)


        location_name_field.editText?.doOnTextChanged { text, start, before, count ->
            ViewModel.locationName.value = text.toString()
        }

        address_field.editText?.doOnTextChanged{ text, start, before, count ->
            ViewModel.address.value = text.toString()

        }


        button_cancel.setOnClickListener{
            dismiss()
        }

        button_add.setOnClickListener{
            ViewModel.addLocation()
            dismiss()
        }
    }
}