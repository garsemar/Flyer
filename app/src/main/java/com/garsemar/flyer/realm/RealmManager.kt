package com.garsemar.flyer.realm

import com.garsemar.flyer.model.Posicions
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
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
    suspend fun login(username: String, password: String) {
        val creds = Credentials.emailPassword(username, password)
        user = realmApp.login(creds)
        configureRealm()
    }

    suspend fun logOut(){
        if(loggedIn()){
            user.logOut()
        }
    }

    suspend fun configureRealm(){
        val user = realmApp.currentUser!!
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