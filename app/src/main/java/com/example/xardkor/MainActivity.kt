package com.example.xardkor

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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

    class DBHelper(context: Context) : SQLiteOpenHelper(context, "mapDB", null, 1) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("create table mappoint (" +
                    "id integer primary key autoincrement," +
                    "lat text," +
                    "lng text," +
                    "weather text);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
