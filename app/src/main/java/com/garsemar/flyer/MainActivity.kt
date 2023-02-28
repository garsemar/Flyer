package com.garsemar.flyer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDirections
import com.garsemar.flyer.databinding.ActivityMainBinding
import com.garsemar.flyer.realm.RealmManager
import androidx.navigation.fragment.findNavController
import com.garsemar.flyer.databinding.FragmentLoginBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var action: NavDirections
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Flyer)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // User: itb, pass: superultrasegurapassword
    }
}

/*class LocationRepository(val realm: Realm){
    fun listFlow() : Flow<List<LocationRealm>> = realm.query<Posicions>().find().asFlow()
    // More location functions
}*/