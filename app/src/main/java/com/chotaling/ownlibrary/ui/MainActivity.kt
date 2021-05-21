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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    var navController : NavController? = null
    var bottomNav : BottomNavigationView? = null
    var toolbar : MaterialToolbar? = null
    var toolbar_search : MaterialButton? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        toolbar_search = findViewById(R.id.toolbar_search)
        toolbar!!.setNavigationOnClickListener { onBackPressed() }
        navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(toolbar)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_book_list, R.id.navigation_locations, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        bottomNav!!.setupWithNavController(navController!!)
        navController!!.addOnDestinationChangedListener(this)


        val isbn = intent.getStringExtra("isbn")
        if (isbn != null)
        {
            val bundle = bundleOf("isbn" to isbn)
            navController!!.navigate(R.id.book_search_results_fragment, bundle)
        }



        toolbar_search!!.setOnClickListener {
            navController!!.navigate(R.id.navigation_book_search_fragment)
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id == R.id.navigation_book_search_fragment)
        {
            toolbar!!.visibility = View.GONE;
        }
        else
        {
            toolbar!!.visibility = View.VISIBLE;
        }
    }


}