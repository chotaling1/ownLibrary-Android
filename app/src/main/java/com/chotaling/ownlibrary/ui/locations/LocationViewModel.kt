package com.chotaling.ownlibrary.ui.locations

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.services.LocationService
import com.chotaling.ownlibrary.ui.BaseViewModel

class LocationViewModel : BaseViewModel() {

    private val _locationService : LocationService = LocationService()
    val locationsList : MutableLiveData<Set<Location>> by lazy {
        MutableLiveData<Set<Location>>()
    }
    override fun viewAppearing(arguments: Bundle?) {
        super.viewAppearing(arguments)
        getLocations()
    }

    fun getLocations()
    {
        val locations = _locationService.getAllLocations();
        locationsList.value = locations?.toSet()
        locations?.addChangeListener { allBooks ->
            locationsList.value = allBooks.toSet()
        }
    }

    fun deleteLocation(location : Location)
    {
        _locationService.removeLocation(location.id)
    }
}