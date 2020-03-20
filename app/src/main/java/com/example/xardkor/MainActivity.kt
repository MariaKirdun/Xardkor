package com.example.xardkor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if( savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, ListFragment())
                .commit()
        }

        mapButton.setOnClickListener { loadFragment(MapFragment()) }
        listButton.setOnClickListener { loadFragment(ListFragment()) }

    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
