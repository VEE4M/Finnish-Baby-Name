package com.gmail.appverstas.finnishbabyname.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Veli-Matti Tikkanen, 19.3.2021
 */
@Entity(tableName = "names_table")
class Name(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val name: String,
        val gender: String,
        var isFavourite: String,
        var isBlacklisted: String
        ) {

}


