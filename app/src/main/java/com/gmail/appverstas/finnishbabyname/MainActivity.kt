package com.gmail.appverstas.finnishbabyname

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.gmail.appverstas.finnishbabyname.data.models.Name
import com.gmail.appverstas.finnishbabyname.data.room.NameViewModel

class MainActivity : AppCompatActivity() {

    private val nameViewModel: NameViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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