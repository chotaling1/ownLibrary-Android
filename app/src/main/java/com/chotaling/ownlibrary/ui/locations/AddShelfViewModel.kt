package com.chotaling.ownlibrary.ui.locations

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.models.Shelf
import com.chotaling.ownlibrary.domain.services.LocationService
import com.chotaling.ownlibrary.ui.BaseViewModel
import io.realm.RealmList
import io.realm.RealmResults

class AddShelfViewModel : BaseViewModel() {

    private val _locationService = LocationService()
    val shelfName : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val selectedLocation : MutableLiveData<Location> by lazy {
        MutableLiveData<Location>()
    }

    val locations : MutableLiveData<Set<Location>> by lazy {
        MutableLiveData<Set<Location>>()
    }

    private var realmLocations : RealmResults<Location>? = null
    override fun viewAppearing(arguments: Bundle?) {
        super.viewAppearing(arguments)

        realmLocations = _locationService.getAllLocations();
        realmLocations?.addChangeListener { allLocations ->
            locations.value = allLocations.toSet()
        }
    }


    fun addShelf()
    {
        val shelf = Shelf()
        shelf.name = shelfName.value!!
        shelf.locationId = selectedLocation.value!!.id
        _locationService.addShelf(shelf, selectedLocation.value!!.id)
    }
}