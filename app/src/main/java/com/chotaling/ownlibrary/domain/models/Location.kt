package com.chotaling.ownlibrary.domain.models

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.bson.types.ObjectId

@Parcelize
open class Location : RealmObject(), Parcelable {

    @PrimaryKey
    var id : ObjectId = ObjectId.get()
    var name : String = String()

    var description : String = String()
    var shelves : RealmList<Shelf> = RealmList<Shelf>()
}