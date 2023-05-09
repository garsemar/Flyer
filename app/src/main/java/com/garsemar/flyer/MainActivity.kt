package com.garsemar.flyer

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.garsemar.flyer.databinding.ActivityMainBinding
import com.garsemar.flyer.realm.RealmManager
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), DrawerLocker {
    companion object {
        val realmManager = ServiceLocator
        lateinit var v_supportActionBar: ActionBar
    }

    lateinit var binding: ActivityMainBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Flyer)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val drawerLayout = binding.drawerLayout
        binding.navigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.mapFragment, R.id.bookmarksFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        v_supportActionBar = supportActionBar!!
        v_supportActionBar.title = "Flyer"
        v_supportActionBar.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setDrawerLocked(shouldLock: Boolean){
        if(shouldLock){
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else{
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

    }
}

/*class LocationRepository(val realm: Realm){
    fun listFlow() : Flow<List<LocationRealm>> = realm.query<Posicions>().find().asFlow()
    // More location functions
}*/