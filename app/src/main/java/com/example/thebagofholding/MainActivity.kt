package com.example.thebagofholding

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var mainActivityLifecycle: MainActivity

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

        //I put this in because sometimes in would crach on load saying lateinnit things weren't loaded.
        //goal was to wait a few seceonds for thnigs to get rocking
        GlobalScope.launch { // launch new coroutine in background and continue
            delay(0) // non-blocking delay for 1 second (default time unit is ms)
            DataMaster.initWith(applicationContext)
        }


        appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_character, R.id.navigation_character_creation_screen))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStop() {
        DataMaster.cleanupHermez()
        super.onStop()
    }

    override fun onDestroy() {
        DataMaster.cleanupHermez()
        super.onDestroy()
    }
}