package com.chotaling.ownlibrary.ui.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.ui.BaseFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView

class LocationFragment : BaseFragment<LocationViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_locations

    private lateinit var recycler_view : RecyclerView
    private lateinit var add_button : FloatingActionButton
    private lateinit var add_location_button : ExtendedFloatingActionButton
    private lateinit var add_shelf_button : ExtendedFloatingActionButton


    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java);
    }

    override fun setupUI() {
        super.setupUI()
        recycler_view = rootView.findViewById(R.id.recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(context)
        add_button = rootView.findViewById(R.id.add_button)
        add_location_button = rootView.findViewById(R.id.add_location_button)
        add_shelf_button = rootView.findViewById(R.id.add_shelf_button)

        add_location_button.setShowMotionSpecResource(R.animator.scale_up)
        add_shelf_button.setShowMotionSpecResource(R.animator.scale_up)

        add_location_button.setHideMotionSpecResource(R.animator.scale_down)
        add_shelf_button.setHideMotionSpecResource(R.animator.scale_down)

        add_button.setOnClickListener{

            if (add_location_button.visibility == View.VISIBLE)
            {
                toggleFabs(false)
            }
            else
            {
                toggleFabs(true)
            }

        }

        add_location_button.setOnClickListener{
            toggleFabs(false)
            this.findNavController().navigate(R.id.action_navigation_locations_to_add_location_fragment)
        }

        add_shelf_button.setOnClickListener{
            toggleFabs(false);
            this.findNavController().navigate(R.id.action_navigation_locations_to_add_shelf_fragment)
        }
    }

    fun toggleFabs(show : Boolean)
    {
        if (show)
        {
            add_location_button.show()
            add_shelf_button.show()
        }
        else
        {
            add_location_button.hide()
            add_shelf_button.hide()
        }
    }

    override fun setupBindings() {
        ViewModel.locationsList.observe(viewLifecycleOwner,  {
            if (!it.isEmpty())
            {
                recycler_view.adapter = LocationListAdapter(it.toList())
            }
        })

    }

    class LocationListAdapter(private val locations : List<Location>) : RecyclerView.Adapter<LocationListAdapter.LocationCellViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LocationCellViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_location, viewGroup, false)
            return LocationCellViewHolder(view)
        }

        override fun onBindViewHolder(holder: LocationCellViewHolder, position: Int) {

            val location = locations[position]
            holder.location_name_text_view.text = location.name
            holder.address_text_view.text = location.address
            holder.number_books_text_view.text = ""
            holder.number_shelves_text_view.text = "${location.shelves?.size} Shelves"

        }

        override fun getItemCount(): Int {
            return locations.size
        }

        class LocationCellViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
            var location_name_text_view : MaterialTextView
            var address_text_view : MaterialTextView
            var number_books_text_view : MaterialTextView
            var number_shelves_text_view : MaterialTextView

            init {
                location_name_text_view = view.findViewById(R.id.location_name_text_view)
                address_text_view = view.findViewById(R.id.address_text_view)
                number_books_text_view = view.findViewById(R.id.number_books_text_view)
                number_shelves_text_view = view.findViewById(R.id.number_shelves_text_view)
            }
        }

    }


}