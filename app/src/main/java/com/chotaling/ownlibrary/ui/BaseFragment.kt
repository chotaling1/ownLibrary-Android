package com.chotaling.ownlibrary.ui

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.chotaling.ownlibrary.R
import com.chotaling.ownlibrary.ui.dashboard.DashboardViewModel

abstract class BaseFragment<T : ViewModel> : Fragment() {

    abstract val rootLayoutId : Int
    protected lateinit var rootView : View
    protected lateinit var ViewModel : T
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        rootView = inflater.inflate(rootLayoutId, container, false)
        setupUI()
        setupBindings()
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    open fun setupUI() {}

    open fun setupBindings() {}
    abstract fun initViewModel()
}