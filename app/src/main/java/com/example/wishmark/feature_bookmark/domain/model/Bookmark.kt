package com.example.wishmark.feature_bookmark.domain.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Bookmark : RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var title: String = ""
    var link: String = ""
    var category: Category? = null
}

class InvalidBookmarkEntryException(message: String): Exception(message)