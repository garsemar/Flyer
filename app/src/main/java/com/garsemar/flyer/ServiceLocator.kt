package com.garsemar.flyer

import com.garsemar.flyer.realm.PosicionsDao
import com.garsemar.flyer.realm.RealmManager

object ServiceLocator {
    val realmManager = RealmManager()
    lateinit var posicionsDao: PosicionsDao
    /**
     * Call when user logged in and realm created
     */
    fun configureRealm(){
        val realm = realmManager.realm!!
        posicionsDao = PosicionsDao(realm, realmManager.realmApp.currentUser!!.id)
    }
}