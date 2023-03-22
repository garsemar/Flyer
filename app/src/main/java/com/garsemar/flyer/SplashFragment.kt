package com.garsemar.flyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.garsemar.flyer.MainActivity.Companion.realmManager
import com.garsemar.flyer.realm.RealmManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashFragment : Fragment() {
    lateinit var action: NavDirections
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!RealmManager().loggedIn()){
            action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        }
        else{
            // action = SplashFragmentDirections.actionSplashFragmentToMapFragment()
            runBlocking{ realmManager.realmManager.configureRealm() }
            action = SplashFragmentDirections.actionSplashFragmentToMapFragment()
        }
        findNavController().navigate(action)
    }
}