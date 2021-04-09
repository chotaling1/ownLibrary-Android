package com.chotaling.ownlibrary.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

class Location : RealmObject() {

    @PrimaryKey
    var id : ObjectId = ObjectId.get()
    var name : String = String()
}