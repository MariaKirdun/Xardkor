package com.example.xardkor

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONObject

class MapFragment : SupportMapFragment(), OnMapReadyCallback {

    companion object {
        const val API_KEY = "2e75bf3f5fb89a60510c91c6a97cef82"
    }

    private lateinit var dbHelper: MainActivity.DBHelper

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MainActivity.DBHelper(activity!!.applicationContext)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        for (marker in getMarkersListFromDB()) {
            map.addMarker(marker)
        }

        setLongMapClick(googleMap)
    }

    private fun setLongMapClick(googleMap: GoogleMap) {
        googleMap.setOnMapLongClickListener { latLng ->
            val weather = getWeather(latLng)
            addToDB(latLng, weather)
            googleMap.addMarker(MarkerOptions()
                .position(latLng)
                .title(weather)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.android)))
        }
    }

    private fun addToDB(latLng: LatLng, weather: String) {
        val cv = ContentValues()
        dbHelper?.let {
            val db = it.writableDatabase
            cv.put("lat", latLng.latitude)
            cv.put("lng", latLng.longitude)
            cv.put("weather", weather)

            db.insert("mappoint", null, cv)
        }
    }

    private fun getMarkersListFromDB() : List<MarkerOptions> {
        val list = ArrayList<MarkerOptions>()
        dbHelper?.let {
            val db = it.writableDatabase
            val cursor = db.query("mappoint", null, null, null, null, null, null)

            if (cursor.moveToFirst()) {
                var latID = cursor.getColumnIndex("lat")
                var lngID = cursor.getColumnIndex("lng")
                var weatherID = cursor.getColumnIndex("weather")

                do {
                    val marker = MarkerOptions()
                        .position(LatLng(cursor.getString(latID).toDouble(), cursor.getString(lngID).toDouble()))
                        .title(cursor.getString(weatherID))
                    list.add(marker)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return list
    }


    private fun getWeather(latLng: LatLng): String {
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${latLng.latitude}&lon=${latLng.longitude}&appid=$API_KEY"

        val getRequest = HttpGetRequest()
        val result = getRequest.execute(url).get()

        val jsonReader = JSONObject(result)
        val arrayWeather = jsonReader.getJSONArray("weather")
        val objJson = arrayWeather.getJSONObject(0)
        return objJson.getString("main")
    }

}
