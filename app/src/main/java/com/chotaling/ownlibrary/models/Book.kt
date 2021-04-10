package com.chotaling.ownlibrary.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

open class Book : RealmObject() {

    @PrimaryKey
    var id : ObjectId = ObjectId.get()

    var isbn : String = ""

    var title: String = ""
    var author: String = ""

    var imageUrl: String = ""

    var locationId : ObjectId? = null
}