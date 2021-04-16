package com.chotaling.ownlibrary.ui.listadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.models.Shelf

class ShelfAdapter(context : Context, @LayoutRes private val layoutResource: Int, private val shelves : List<Shelf>) :
    Filterable,
    ArrayAdapter<Shelf>(context, layoutResource, shelves) {

    private var _shelves : List<Shelf> = shelves

    override fun getCount(): Int {
        return _shelves.size
    }

    override fun getItem(position: Int): Shelf? {
        return _shelves[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(layoutResource, parent, false) as TextView
        view.text = _shelves[position].name
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                _shelves = filterResults.values as List<Shelf>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString== null || queryString.isEmpty())
                    shelves
                else
                    shelves.filter {
                        it.name.toLowerCase().contains(queryString)
                    }
                return filterResults
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                val shelf = resultValue as Shelf;
                return shelf.name
            }
        }
    }
}