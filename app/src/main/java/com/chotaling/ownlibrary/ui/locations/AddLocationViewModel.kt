package com.chotaling.ownlibrary.ui.locations

import androidx.lifecycle.MutableLiveData
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.services.LocationService
import com.chotaling.ownlibrary.ui.BaseViewModel

class AddLocationViewModel : BaseViewModel() {
    private val _locationService = LocationService()
    val locationName : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    val address : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    fun addLocation()
    {
        val location = Location()
        location.name = locationName.value!!
        location.address = address.value!!
        _locationService.addLocation(location)

    }
}