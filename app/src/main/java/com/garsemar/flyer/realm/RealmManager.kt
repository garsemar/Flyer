package com.garsemar.flyer.realm

import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.garsemar.flyer.HomeFragmentDirections
import io.realm.kotlin.Realm
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*

class RealmManager{
    val realmApp = App.create(AppConfiguration.Builder("flyer-idoiv").build())
    var realm : Realm? = null
    lateinit var action: NavDirections

    fun loggedIn() = realmApp.currentUser?.loggedIn ?: false
    suspend fun login(username: String, password: String): Boolean{
        realmApp.login(Credentials.emailPassword(username, password))
        return loggedIn()
    }
    suspend fun register(username: String, password: String): Boolean {
        realmApp.emailPasswordAuth.registerUser(username, password)
        login(username, password)
        return loggedIn()
    }
}