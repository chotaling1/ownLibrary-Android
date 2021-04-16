package com.chotaling.ownlibrary.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class Book : RealmObject() {

    @PrimaryKey
    var id : ObjectId = ObjectId.get()

    var isbn : String = ""

    var title: String = ""
    var author: String = ""
    var publisher : String = ""
    var imageUrl: String = ""

    var shelf : Shelf? = null
}