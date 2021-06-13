package com.gmail.appverstas.finnishbabyname.data.room

import com.gmail.appverstas.finnishbabyname.data.models.Name

/**
 *Veli-Matti Tikkanen, 19.3.2021
 */
class NameRepository(private val nameDao: NameDao) {

    var getAllNameData = nameDao.getAllData()



    suspend fun insertName(name: Name){
        nameDao.insertName(name)
    }

    suspend fun updateName(name: Name){
        nameDao.updateName(name)
    }

    suspend fun deleteName(name: Name){
        nameDao.deleteName(name)
    }

}