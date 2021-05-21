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

class LocationAdapter(context : Context, @LayoutRes private val layoutResource: Int, private var locations : List<Location>) :
    Filterable,
    ArrayAdapter<Location>(context, layoutResource, locations) {

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
        view.text = locations[position].name
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                locations = filterResults.values as List<Location>
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

    fun getIndexOfLocation(location : Location) : Int
    {
        return locations.indexOf(location)
    }
}