package com.chotaling.ownlibrary.ui.books

import android.app.ProgressDialog
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.fragment.findNavController
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.ui.BaseDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class BookSearchDialogFragment : BaseDialogFragment<BookSeachViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_dialog_book_lookup

    private lateinit var isbn_lookup_field : TextInputLayout
    private lateinit var title_field : TextInputLayout
    private lateinit var author_field : TextInputLayout
    private lateinit var button_cancel : MaterialButton
    private lateinit var button_search : MaterialButton
    private lateinit var button_manual_add : MaterialButton
    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(BookSeachViewModel::class.java);
    }

    override fun setupUI() {
        isbn_lookup_field = rootView.findViewById(R.id.isbn_lookup_field)
        title_field = rootView.findViewById(R.id.title_field)
        author_field = rootView.findViewById(R.id.author_field)
        button_cancel = rootView.findViewById(R.id.button_cancel)
        button_search = rootView.findViewById(R.id.button_search)
        button_manual_add = rootView.findViewById(R.id.button_manual_add)

        isbn_lookup_field.setEndIconOnClickListener{
            findNavController().navigate(R.id.activity_barcode_scanner)
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
            val progressDialog = ProgressDialog(context)
            progressDialog.show()
            lifecycleScope.launch {
                var result = ViewModel.lookupBook()
                progressDialog.dismiss()

                var bundle = bundleOf()
                bundle.putParcelableArray("BookResults", result)
                findNavController().navigate(R.id.book_search_results_fragment, bundle)
            }
        }
    }
}