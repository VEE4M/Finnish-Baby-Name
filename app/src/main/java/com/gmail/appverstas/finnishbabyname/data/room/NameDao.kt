package com.gmail.appverstas.finnishbabyname.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gmail.appverstas.finnishbabyname.data.models.Name

/**
 *Veli-Matti Tikkanen, 19.3.2021
 */
@Dao
interface NameDao {


    @Query("SELECT * FROM names_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<Name>>

    @Insert
    suspend fun insertName(name: Name)

    @Update
    suspend fun updateName(name: Name)

    @Delete
    suspend fun deleteName(name: Name)


}