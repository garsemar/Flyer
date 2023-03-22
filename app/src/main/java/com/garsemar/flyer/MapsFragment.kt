package com.garsemar.flyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize view
        val view: View = inflater.inflate(R.layout.fragment_maps, container, false)

        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        var cords: LatLng? = null

        // Async map
        supportMapFragment!!.getMapAsync { googleMap ->
            // When map is loaded
            googleMap.setOnMapClickListener { latLng -> // When clicked on map
                cords = latLng
                // Initialize marker options
                val markerOptions = MarkerOptions()
                // Set position of marker
                markerOptions.position(latLng)
                // Set title of marker
                markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
                // Remove all marker
                googleMap.clear()
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                // Add marker on map
                googleMap.addMarker(markerOptions)
            }
        }

        view.findViewById<FloatingActionButton>(R.id.addMark).setOnClickListener {
            if(cords != null){
                println("${cords!!.latitude}, ${cords!!.longitude}")
                val action = MapFragmentDirections.actionMapFragmentToAddFragment()
                action.lat = cords!!.latitude.toString()
                action.lan = cords!!.longitude.toString()
                findNavController().navigate(action)
            }
        }

        // Return view
        return view
    }
}