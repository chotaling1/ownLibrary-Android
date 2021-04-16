package com.chotaling.ownlibrary.ui.locations

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.ui.BaseDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class AddLocationDialogFragment : BaseDialogFragment<AddLocationViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_dialog_location_add

    @BindView(R.id.location_name_field) lateinit var location_name_field : TextInputLayout
    @BindView(R.id.description_field) lateinit var description_field : TextInputLayout
    @BindView(R.id.button_cancel) lateinit var button_cancel : MaterialButton
    @BindView(R.id.button_add) lateinit var button_add : MaterialButton

    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(AddLocationViewModel::class.java)
    }

    override fun setupBindings() {
        super.setupBindings()

        ViewModel.editMode.observe(viewLifecycleOwner,  {
            if (it)
            {
                button_add.setText(R.string.update)
            }
            else
            {
                button_add.setText(R.string.add)
            }
        })
    }

    override fun setupUI() {
        location_name_field.editText?.setText(ViewModel.locationName.value)
        description_field.editText?.setText(ViewModel.description.value)


        location_name_field.editText?.doOnTextChanged { text, start, before, count ->
            ViewModel.locationName.value = text.toString()
        }

        description_field.editText?.doOnTextChanged{ text, start, before, count ->
            ViewModel.description.value = text.toString()

        }


        button_cancel.setOnClickListener{
            dismiss()
        }

        button_add.setOnClickListener{
            if (ViewModel.valid())
            {
                location_name_field.isErrorEnabled = false
                ViewModel.setLocation()
                dismiss()
            }
            else
            {
                location_name_field.error = "Required"
            }

        }
    }
}