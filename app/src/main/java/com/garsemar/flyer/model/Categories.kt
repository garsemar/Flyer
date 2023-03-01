package com.garsemar.flyer.model

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Categories(
    @PrimaryKey
    var _id: ObjectId = ObjectId.create(),
    var title: String = "",
    var description: String = "",
    var owner_id: String = ""
) : RealmObject {
    constructor() : this(owner_id = "")
}