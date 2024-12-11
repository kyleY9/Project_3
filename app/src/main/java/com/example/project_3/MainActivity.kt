package com.example.project_3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var navController: NavController
    private var wpm: Double = 0.0
    private var highestWPM: Double = Double.MIN_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(appBar)
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph: NavGraph = navController.graph
        appBarConfig = AppBarConfiguration(navGraph)
        setupActionBarWithNavController(navController, appBarConfig)

        // Data Persistence
        val prefs: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        highestWPM = prefs.getFloat("highestWPM", 0.0F).toDouble()

    }

    override fun onSupportNavigateUp(): Boolean {
        val success: Boolean = navController.navigateUp(appBarConfig)
        return success || super.onSupportNavigateUp()
    }

    override fun onStop() {
        super.onStop()
        val prefs: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val prefEditor: SharedPreferences.Editor = prefs.edit()
        prefEditor.putFloat("highestWPM", highestWPM.toFloat())
        prefEditor.apply()
    }

    fun getWPM(): Double {
        return wpm
    }

    fun setWPM(temp: Double) {
        wpm = temp
    }

    fun getHighestWPM(): Double {
        return highestWPM
    }

    fun setHighestWPM(temp: Double) {
        highestWPM = temp
    }

}