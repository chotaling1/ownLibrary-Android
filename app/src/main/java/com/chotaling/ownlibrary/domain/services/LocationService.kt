package com.chotaling.ownlibrary.domain.services

import com.chotaling.ownlibrary.domain.RealmConfig
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.domain.models.Shelf
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.where
import org.bson.types.ObjectId

class LocationService() {

    private var realmInstance : Realm = RealmConfig().getInstance()

    fun addLocation(location : Location)
    {
        realmInstance.executeTransactionAsync() { realm ->
            realm.insert(location)
        }
    }

    fun removeLocation(location : Location)
    {
        realmInstance.executeTransactionAsync { realm ->
            location.deleteFromRealm()
        }
    }

    fun getAllLocations() : RealmResults<Location>?
    {
        return realmInstance.where<Location>().findAllAsync()
    }

    fun updateLocation(location : Location)
    {
        realmInstance.executeTransactionAsync { realm ->
            val managedLocation = realm.where<Location>().equalTo("id", location.id).findFirstAsync()
            realmInstance.executeTransactionAsync { realm ->
                managedLocation?.name = location.name
            }
        }
    }

    fun addShelf(shelf : Shelf, locationId : ObjectId)
    {
        realmInstance.executeTransactionAsync { realm ->
            realm.insert(shelf)
            val innerLocation = realm.where<Location>().equalTo("id", locationId).findFirst()
            innerLocation?.shelves?.add(shelf)
        }
    }
}