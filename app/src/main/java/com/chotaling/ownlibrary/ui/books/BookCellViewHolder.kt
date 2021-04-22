package com.chotaling.ownlibrary.ui.books

import android.graphics.Bitmap
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import butterknife.BindView
import butterknife.ButterKnife
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.chotaling.ownlibrary.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class BookCellViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
    private var _extendedViewsVisible : Boolean = false
    var extendedViewsVisible : Boolean
        get() {
            return _extendedViewsVisible
        }
        set(value) {

            if (!value)
            {
                more_options_image.visibility = View.VISIBLE
                edit_button.visibility = View.GONE
                delete_button.visibility = View.GONE
                extended_info_layout.visibility = View.GONE
            }
            else
            {
                more_options_image.visibility = View.GONE
                extended_info_layout.visibility = View.VISIBLE
                edit_button.visibility = View.VISIBLE
                delete_button.visibility = View.VISIBLE
            }

            _extendedViewsVisible = value
        }
    @BindView(R.id.book_image_view) lateinit var imageView : ImageView
    @BindView(R.id.author_description_text_view) lateinit var authorTextView : MaterialTextView
    @BindView(R.id.title_description_text_view) lateinit var titleTextView : MaterialTextView
    @BindView(R.id.edit_button) lateinit var edit_button : MaterialButton
    @BindView(R.id.delete_button) lateinit var delete_button : MaterialButton
    @BindView(R.id.button_layout) lateinit var button_layout : ConstraintLayout
    @BindView(R.id.more_options_image) lateinit var more_options_image : ImageView
    @BindView(R.id.extended_info_layout) lateinit var extended_info_layout : ConstraintLayout
    @BindView(R.id.publisher_text_view) lateinit var publisher_text_view : MaterialTextView
    @BindView(R.id.publisher_description_text_view) lateinit var publisher_description_text_view : MaterialTextView
    @BindView(R.id.shelf_text_view) lateinit var shelf_text_view : MaterialTextView

    init {
        ButterKnife.bind(this, view)
    }

    fun loadImageView(url: String) {

        val queue = Volley.newRequestQueue(view.context)
        val imageRequest = ImageRequest(url,
            { response ->
                imageView.setImageBitmap(response)
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            Bitmap.Config.RGB_565,
            {
                imageView.setImageResource(R.drawable.ic_launcher_background)
            })

        queue.add(imageRequest)
    }

    fun toggleExtendedViews() {

        val transition : Transition = Slide(Gravity.BOTTOM)
        transition.startDelay = 100
        transition.duration = 200
        transition.addTarget(more_options_image)
        transition.addTarget(edit_button)
        transition.addTarget(delete_button)
        transition.addTarget(extended_info_layout)
        TransitionManager.beginDelayedTransition(button_layout as ViewGroup, transition)


        extendedViewsVisible = !extendedViewsVisible
    }
}