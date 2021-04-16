package com.chotaling.ownlibrary.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.chotaling.ownlibrary.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    var navController : NavController? = null
    var bottomNav : BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_book_list, R.id.navigation_locations, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        bottomNav!!.setupWithNavController(navController!!)

        val barcodes = intent.getStringArrayExtra("BarcodeResults")
        if (barcodes != null)
        {
            val bundle = bundleOf("BarcodeResults" to barcodes)
            navController!!.navigate(R.id.book_search_results_fragment, bundle)
        }
    }


}