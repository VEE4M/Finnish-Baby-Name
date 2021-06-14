package com.gmail.appverstas.finnishbabyname.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmail.appverstas.finnishbabyname.data.models.Name


/**
 *Veli-Matti Tikkanen, 19.3.2021
 */

@Database(entities = [Name::class],version = 1,exportSchema = false)
abstract class NameDatabase: RoomDatabase() {

    abstract fun nameDao(): NameDao

    companion object{

        @Volatile
        private var INSTANCE: NameDatabase? = null

        fun getDatabase(context: Context): NameDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NameDatabase::class.java,
                        "names_db"
                ).build()
                INSTANCE = instance
                return instance
            }

       }

        fun addToDatabase(context: Context, fileName: String, gender: String, nameViewModel: NameViewModel){
            val names = context?.assets?.open(fileName)?.reader()?.readLines()
            if (names != null) {
                for (name in names) {
                    val newName = Name(
                        0,
                        name,
                        gender,
                        "false",
                        "false")
                    nameViewModel.insertName(newName)
                }
            }
        }
    }

}