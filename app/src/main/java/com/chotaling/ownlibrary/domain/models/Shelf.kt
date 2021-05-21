package com.chotaling.ownlibrary.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class Shelf : RealmObject() {

    @PrimaryKey
    var id : ObjectId = ObjectId.get()
    var name : String = String()
    var locationId : ObjectId? = null;
}