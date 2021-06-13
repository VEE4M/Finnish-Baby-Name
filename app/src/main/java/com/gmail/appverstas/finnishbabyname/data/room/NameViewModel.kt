package com.gmail.appverstas.finnishbabyname.data.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gmail.appverstas.finnishbabyname.data.models.Name
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *Veli-Matti Tikkanen, 19.3.2021
 */
class NameViewModel(application: Application): AndroidViewModel(application) {

    private val nameDao = NameDatabase.getDatabase(application).nameDao()
    private val repository: NameRepository

    val getAllData: LiveData<List<Name>>

    init{
        repository = NameRepository(nameDao)
        getAllData = repository.getAllNameData
    }

    fun insertName(name: Name){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertName(name)
        }
    }

    fun updateName(name: Name){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateName(name)
        }
    }

    fun deleteName(name: Name){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteName(name)
        }
    }


}