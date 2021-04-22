package com.chotaling.ownlibrary.ui.locations

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.services.LocationService
import com.chotaling.ownlibrary.ui.BaseViewModel

class AddLocationViewModel : BaseViewModel() {
    private val _locationService = LocationService()
    private var _location : Location? = null

    val editMode = MutableLiveData<Boolean>().apply { value = false }

    val locationName : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    val description = MutableLiveData<String>().apply {
        value = ""
    }


    override fun viewAppearing(arguments: Bundle?) {
        super.viewAppearing(arguments)
        if (arguments != null &&
                arguments.containsKey("Location"))
        {
            _location = arguments.get("Location") as Location
            locationName.value = _location!!.name
            description.value = _location!!.description
            editMode.value = true
        }
    }

    fun setLocation()
    {
        if (!editMode.value!!)
        {
            addLocation()
        }
        else
        {
            updateLocation()
        }
    }

    private fun addLocation()
    {
        val location = Location()
        location.name = locationName.value!!
        location.description = if (description.value.isNullOrBlank()) "" else description.value!!
        _locationService.addLocation(location)
    }

    private fun updateLocation()
    {
        _locationService.updateLocation(_location!!.id, locationName.value!!, description.value!!)
    }

    fun valid() : Boolean {
        return !locationName.value.isNullOrEmpty();
    }
}