package com.example.thebagofholding

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each

        //toolbar
//        this.supportActionBar?.setCustomView(R.layout.toolbar)
//        this.supportActionBar?.setDisplayShowCustomEnabled(true)
//        this.supportActionBar?.elevation = 0.0f

//         menu should be considered as top level destinations.

        //create dataMaster
        //TODO how do i pass this around? Is this even instantiated???
        DataMaster.initWith(application)


        appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_character, R.id.navigation_character_creation_screen))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}