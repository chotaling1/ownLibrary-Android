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
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.ui.BaseDialogFragment
import com.chotaling.ownlibrary.ui.BaseViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class AddShelfDialogFragment : BaseDialogFragment<AddShelfViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_dialog_shelf_add

    private lateinit var shelf_name_field : TextInputLayout
    private lateinit var location_field : TextInputLayout
    private lateinit var button_cancel : MaterialButton
    private lateinit var button_add : MaterialButton



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
        shelf_name_field = rootView.findViewById(R.id.shelf_name_field)
        location_field = rootView.findViewById(R.id.location_field)
        button_cancel = rootView.findViewById(R.id.button_cancel)
        button_add = rootView.findViewById(R.id.button_add)


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


    private class LocationAdapter(context : Context, @LayoutRes private val layoutResource: Int, private val locations : List<Location>) :
        Filterable,
        ArrayAdapter<Location>(context, layoutResource, locations) {

        private var _locations : List<Location> = locations

        override fun getCount(): Int {
            return locations.size
        }

        override fun getItem(position: Int): Location? {
            return locations[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(layoutResource, parent, false) as TextView
            view.text = _locations[position].name
            return view
        }

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                    _locations = filterResults.values as List<Location>
                    notifyDataSetChanged()
                }

                override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                    val queryString = charSequence?.toString()?.toLowerCase()

                    val filterResults = Filter.FilterResults()
                    filterResults.values = if (queryString== null || queryString.isEmpty())
                        locations
                    else
                        locations.filter {
                            it.name.toLowerCase().contains(queryString)
                        }
                    return filterResults
                }

                override fun convertResultToString(resultValue: Any?): CharSequence {
                    val location = resultValue as Location;
                    return location.name
                }
            }
        }

    }

}