package com.chotaling.ownlibrary.ui.books

import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.ui.BaseDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class BookLookupDialogFragment : BaseDialogFragment<BookLookupDialogViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_dialog_book_lookup

    @BindView(R.id.isbn_lookup_field) lateinit var isbn_lookup_field : TextInputLayout
    @BindView(R.id.title_field) lateinit var title_field : TextInputLayout
    @BindView(R.id.author_field) lateinit var author_field : TextInputLayout
    @BindView(R.id.button_cancel) lateinit var button_cancel : MaterialButton
    @BindView(R.id.button_search) lateinit var button_search : MaterialButton
    @BindView(R.id.button_manual_add) lateinit var button_manual_add : MaterialButton

    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookLookupDialogViewModel::class.java);
    }

    override fun setupUI() {
        isbn_lookup_field = rootView.findViewById(R.id.isbn_lookup_field)
        title_field = rootView.findViewById(R.id.title_field)
        author_field = rootView.findViewById(R.id.author_field)
        button_cancel = rootView.findViewById(R.id.button_cancel)
        button_search = rootView.findViewById(R.id.button_search)
        button_manual_add = rootView.findViewById(R.id.button_manual_add)

        isbn_lookup_field.setEndIconOnClickListener{
            findNavController().navigate(R.id.action_addBookDialogFragment_to_activity_barcode_scanner)
        }

        isbn_lookup_field.editText?.doOnTextChanged { text, start, before, count ->
            ViewModel.isbn.value = text.toString()
        }

        title_field.editText?.doOnTextChanged{ text, start, before, count ->
            ViewModel.title.value = text.toString()

        }

        author_field.editText?.doOnTextChanged { text, start, before, count ->
            ViewModel.author.value = text.toString()
        }

        button_manual_add.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_dashboard_to_addBookDialogFragment)
        }

        button_cancel.setOnClickListener{
            dismiss()
        }

        button_search.setOnClickListener{
            var bundle = bundleOf()
            bundle.putString("title", ViewModel.title.value)
            bundle.putString("author", ViewModel.author.value)
            bundle.putString("isbn", ViewModel.isbn.value)
            findNavController().navigate(R.id.book_search_results_fragment, bundle)
        }
    }
}