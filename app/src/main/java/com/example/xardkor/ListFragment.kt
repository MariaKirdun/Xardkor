package com.example.xardkor

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

    private lateinit var adapter: MapPointsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MapPointsAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this.context)

        adapter.dataset = listOf("Absolut", "Beee", "cxsfC")
        adapter.notifyDataSetChanged()
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
