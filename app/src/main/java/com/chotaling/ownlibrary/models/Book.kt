package com.chotaling.ownlibrary.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

class Book : RealmObject() {

    @PrimaryKey
    var id : UUID = UUID.randomUUID()

    var isbn : String = ""

    var title: String = ""
    var author: String = ""

    var imageUrl: String = ""

    var locationId : ObjectId? = null
}