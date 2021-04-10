package com.chotaling.ownlibrary.ui.dashboard

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.ui.BaseDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class AddBookDialogFragment : BaseDialogFragment<AddBookViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_dialog_book_lookup

    private lateinit var isbn_lookup_field : TextInputLayout
    private lateinit var title_field : TextInputLayout
    private lateinit var author_field : TextInputLayout
    private lateinit var button_cancel : MaterialButton
    private lateinit var button_search : MaterialButton
    private lateinit var button_manual_add : MaterialButton
    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(AddBookViewModel::class.java);
    }

    override fun setupUI() {
        isbn_lookup_field = rootView.findViewById(R.id.isbn_lookup_field)
        title_field = rootView.findViewById(R.id.title_field)
        author_field = rootView.findViewById(R.id.author_field)
        button_cancel = rootView.findViewById(R.id.button_cancel)
        button_search = rootView.findViewById(R.id.button_search)
        button_manual_add = rootView.findViewById(R.id.button_manual_add)

        isbn_lookup_field.setEndIconOnClickListener{
            //TODO: Barcode scanner
        }



    }
}