package com.garsemar.flyer.model

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Posicions(
    @PrimaryKey
    var _id: ObjectId = ObjectId.create(),
    var title: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var image: ByteArray? = null,
    var owner_id: String = ""
) : RealmObject {
    // Declaring empty contructor
    constructor() : this(owner_id = "") {}
    //var doAfter: RealmList<Item>? = realmListOf()
    override fun toString() = "Posicions($title, Cords($lat, $lon))"
}