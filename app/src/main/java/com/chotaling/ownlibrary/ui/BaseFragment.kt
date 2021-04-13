package com.chotaling.ownlibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    abstract val rootLayoutId : Int
    protected lateinit var rootView : View
    protected lateinit var ViewModel : T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initViewModel()
        rootView = inflater.inflate(rootLayoutId, container, false)
        setupUI()

        return rootView
    }

    override fun onResume() {
        super.onResume()
        setupBindings()
        ViewModel.viewAppearing(arguments)
    }


    open fun setupUI() {}
    open fun setupBindings() {}
    abstract fun initViewModel()
}