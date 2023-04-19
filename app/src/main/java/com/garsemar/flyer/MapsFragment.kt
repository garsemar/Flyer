package com.garsemar.flyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.garsemar.flyer.MainActivity.Companion.realmManager
import com.garsemar.flyer.MainActivity.Companion.v_supportActionBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v_supportActionBar.show()
        v_supportActionBar.title = "Flyer"
        // Initialize view
        val view: View = inflater.inflate(R.layout.fragment_maps, container, false)

        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        var cords: LatLng? = null

        // Async map
        supportMapFragment!!.getMapAsync { googleMap ->
            // When map is loaded
            updateUi(googleMap)

            googleMap.setOnMarkerClickListener { marker ->
                if (marker.isInfoWindowShown) {
                    marker.hideInfoWindow()
                } else {
                    marker.showInfoWindow()
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
                true
            }

            googleMap.setOnMapClickListener { latLng -> // When clicked on map
                cords = latLng
                // Initialize marker options
                val markerOptions = MarkerOptions()
                // Set position of marker
                markerOptions.position(latLng)
                // Set title of marker
                markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
                googleMap.clear()
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                // Add marker on map
                googleMap.addMarker(markerOptions)

                updateUi(googleMap)
            }
        }

        view.findViewById<FloatingActionButton>(R.id.addMark).setOnClickListener {
            if(cords != null){
                if(realmManager.realmManager.realm != null){
                    val action = MapFragmentDirections.actionMapFragmentToAddFragment()
                    action.lat = cords!!.latitude.toString()
                    action.lan = cords!!.longitude.toString()
                    findNavController().navigate(action)
                }
            }
        }

        // Return view
        return view
    }
    fun updateUi(googleMap: GoogleMap){
        GlobalScope.launch(Dispatchers.Main.immediate) {
            realmManager.realmManager.configureRealm()
            realmManager.configureRealm()
            while (realmManager.realmManager.realm == null){
                delay(500)
                println(realmManager.realmManager.realm)
            }
            val books = realmManager.posicionsDao.listFlow()
            books.forEach {
                val latLng = LatLng(it.lat, it.lon)

                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.title(it.title)
                googleMap.addMarker(markerOptions)
            }
        }
    }
}