package com.example.xardkor

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : SupportMapFragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        setLongMapClick(googleMap)
    }

    private fun setLongMapClick(googleMap: GoogleMap) {
        googleMap.setOnMapLongClickListener { latLng ->
            googleMap.addMarker(MarkerOptions()
                .position(latLng)
                .title("Dropped Pin")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.android)))
        }
    }


}
