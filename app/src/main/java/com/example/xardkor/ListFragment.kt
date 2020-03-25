package com.example.xardkor

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*

class ListFragment: Fragment() {

    private lateinit var dbHelper: MainActivity.DBHelper

    private lateinit var adapter: MapPointsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dbHelper = MainActivity.DBHelper(activity!!.applicationContext)
        adapter = MapPointsAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this.context)

        adapter.dataset = getDataFromDB()
        adapter.notifyDataSetChanged()
    }

    private fun getDataFromDB() : List<String> {
        val list = ArrayList<String>()
        dbHelper?.let {
            val db = it.writableDatabase
            val cursor = db.query("mappoint", null, null, null, null, null, null)

            if (cursor.moveToFirst()) {
                var latID = cursor.getColumnIndex("lat")
                var lngID = cursor.getColumnIndex("lng")
                var weatherID = cursor.getColumnIndex("weather")

                do {
                    val str = "Lat: ${cursor.getString(latID)} Lng: ${cursor.getString(lngID)} Weather: ${cursor.getString(weatherID)}"
                    list.add(str)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return list
    }

    class MapPointsAdapter() :
            RecyclerView.Adapter<MapPointsAdapter.MapPointViewHolder>() {

        var dataset: List<String> = listOf()

        class MapPointViewHolder(val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapPointViewHolder {
            return MapPointViewHolder(
                LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            )
        }

        override fun getItemCount(): Int = dataset.size

        override fun onBindViewHolder(holder: MapPointViewHolder, position: Int) {
            holder.view.textView.text = dataset[position]
        }
    }
}
