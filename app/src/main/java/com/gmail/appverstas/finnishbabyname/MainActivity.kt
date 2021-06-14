package com.gmail.appverstas.finnishbabyname

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gmail.appverstas.finnishbabyname.data.models.Name
import com.gmail.appverstas.finnishbabyname.data.room.NameViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val nameViewModel: NameViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.nameListFragment, R.id.favouriteNamesFragment, R.id.nameGeneratorFragment))
        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun addToFavourites(name: Name){
        name.isFavourite = "true"
        name.isBlacklisted = "false"
        nameViewModel.updateName(name)
    }

    fun deleteFromFavourites(name: Name){
        name.isFavourite = "false"
        nameViewModel.updateName(name)
    }

    fun addToBlacklist(name: Name){
        name.isBlacklisted = "true"
        name.isFavourite = "false"
        nameViewModel.updateName(name)
    }

    fun deleteFromBlacklist(name: Name){
        name.isBlacklisted = "false"
        nameViewModel.updateName(name)
    }

}