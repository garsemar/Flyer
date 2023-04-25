package com.garsemar.flyer.realm

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.garsemar.flyer.model.Posicions
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
import io.realm.kotlin.mongodb.exceptions.InvalidCredentialsException
import io.realm.kotlin.mongodb.sync.SyncConfiguration

class RealmManager {
    val realmApp = App.create(AppConfiguration.Builder("flyer-idoiv").log(LogLevel.ALL).build())
    var realm : Realm? = null
    lateinit var user: User

    fun loggedIn() = realmApp.currentUser?.loggedIn ?: false

    suspend fun register(username: String, password: String) {
        realmApp.emailPasswordAuth.registerUser(username, password)
        login(username, password)
    }
    suspend fun login(username: String, password: String): Boolean {
        val creds = Credentials.emailPassword(username, password)
        try {
            user = realmApp.login(creds)
        } catch (e: InvalidCredentialsException){
            return false
        }
        configureRealm()
        return true
    }

    suspend fun logOut(){
        if(loggedIn()){
            user.logOut()
        }
    }

    suspend fun configureRealm(){
        user = realmApp.currentUser!!
        val config = SyncConfiguration.Builder(user, setOf(Posicions::class))
            .initialSubscriptions { realm ->
                add(
                    realm.query<Posicions>(),
                    "All Items"
                )
            }
            .waitForInitialRemoteData()
            .build()
        realm = Realm.open(config)
        realm!!.subscriptions.waitForSynchronization()
        //ServiceLocator.configureRealm()
    }
}