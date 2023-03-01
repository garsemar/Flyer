package com.garsemar.flyer.realm

import com.garsemar.flyer.model.Posicions
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PosicionsDao(val realm: Realm, val userId: String){

    fun listFlow() : Flow<List<Posicions>> = realm.query<Posicions>().find().asFlow().map { it.list.toList() }

    fun insertItem(title: String, lat: Double, lon: Double, image: ByteArray){
        realm.writeBlocking {
            val item = Posicions(title = title, lat = lat, lon = lon, image = image, owner_id = userId)
            copyToRealm(item)
        }
    }
}