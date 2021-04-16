package com.chotaling.ownlibrary.ui.locations

import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import butterknife.BindView
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.services.BookService
import com.chotaling.ownlibrary.domain.services.LocationService
import com.chotaling.ownlibrary.ui.BaseFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_book_list.view.*

class LocationFragment : BaseFragment<LocationViewModel>() {
    override val rootLayoutId: Int
        get() = R.layout.fragment_locations

    @BindView(R.id.recycler_view) lateinit var recycler_view : RecyclerView
    @BindView(R.id.add_button) lateinit var add_button : FloatingActionButton
    @BindView(R.id.add_location_button) lateinit var add_location_button : ExtendedFloatingActionButton
    @BindView(R.id.add_shelf_button) lateinit var add_shelf_button : ExtendedFloatingActionButton


    override fun initViewModel() {
        ViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java);
    }

    override fun setupUI() {
        super.setupUI()

        recycler_view.layoutManager = LinearLayoutManager(context)
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
            if (!ViewModel.locationsList.value.isNullOrEmpty())
            {
                add_shelf_button.show()
            }

        }
        else
        {
            add_location_button.hide()
            if (!ViewModel.locationsList.value.isNullOrEmpty())
            {
                add_shelf_button.hide()
            }

        }
    }

    override fun setupBindings() {
        ViewModel.locationsList.observe(viewLifecycleOwner,  {
            recycler_view.adapter = LocationListAdapter(it.toList(), this)
        })
    }

    class LocationListAdapter(private val locations : List<Location>, private val owner : LocationFragment) : RecyclerView.Adapter<LocationListAdapter.LocationCellViewHolder>() {

        private var _bookService = BookService()
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LocationCellViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_location, viewGroup, false)
            return LocationCellViewHolder(view)
        }

        override fun onBindViewHolder(holder: LocationCellViewHolder, position: Int) {

            val location = locations[position]
            holder.location_name_text_view.text = location.name
            holder.description_text_view.text = location.description
            if (location.shelves?.size == 1)
            {
                holder.number_shelves_text_view.text = "${location.shelves?.size} Shelf"
            }
            else
            {
                holder.number_shelves_text_view.text = "${location.shelves?.size} Shelves"
            }

            val books = _bookService.getBooksByLocation(location)
            if (books?.size == 1)
            {
                holder.number_books_text_view.text = "${books?.size} Book"
            }
            else
            {
                holder.number_books_text_view.text = "${books?.size} Books"
            }

            holder.view.setOnClickListener {
                holder.toggleExtendedViews()
            }

            holder.shelves_button.setOnClickListener {

            }

            holder.edit_button.setOnClickListener{
                val bundle = bundleOf("Location" to location)
                holder.view.findNavController().navigate(R.id.action_navigation_locations_to_add_location_fragment, bundle)
            }

            holder.delete_button.setOnClickListener{

                AlertDialog.Builder(owner.requireContext())
                    .setMessage(R.string.delete_confirmation)
                    .setPositiveButton(R.string.yes, object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            owner.ViewModel.deleteLocation(location)
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .show()
            }
        }

        override fun getItemCount(): Int {
            return locations.size
        }

        class LocationCellViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
            var location_name_text_view : MaterialTextView
            var description_text_view : MaterialTextView
            var number_books_text_view : MaterialTextView
            var number_shelves_text_view : MaterialTextView
            var button_layout : ConstraintLayout
            var shelves_button : MaterialButton
            var edit_button : MaterialButton
            var delete_button : MaterialButton
            var shelf_recycler_view : RecyclerView

            init {
                location_name_text_view = view.findViewById(R.id.location_name_text_view)
                description_text_view = view.findViewById(R.id.description_text_view)
                number_books_text_view = view.findViewById(R.id.number_books_text_view)
                number_shelves_text_view = view.findViewById(R.id.number_shelves_text_view)
                button_layout = view.findViewById(R.id.button_layout)
                shelves_button = view.findViewById(R.id.shelves_button)
                edit_button = view.findViewById(R.id.edit_button)
                delete_button = view.findViewById(R.id.delete_button)
                shelf_recycler_view = view.findViewById(R.id.shelf_recycler_view)
            }

            fun toggleExtendedViews() {
                val transition : Transition = Slide(Gravity.TOP)
                transition.startDelay = 100
                transition.duration = 200
                transition.addTarget(edit_button)
                transition.addTarget(delete_button)
                transition.addTarget(shelves_button)
                TransitionManager.beginDelayedTransition(button_layout as ViewGroup, transition)
                if (edit_button.visibility == View.VISIBLE)
                {
                    edit_button.visibility = View.GONE
                    delete_button.visibility = View.GONE
                    shelves_button.visibility = View.GONE
                }
                else
                {
                    edit_button.visibility = View.VISIBLE
                    delete_button.visibility = View.VISIBLE
                    shelves_button.visibility = View.VISIBLE
                }

            }
        }

    }


}