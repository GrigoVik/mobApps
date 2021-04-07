package com.example.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ItemDBView(application: Application): AndroidViewModel(application) {

    private val repository: ItemRepo

    init {
        val itemDao = ItemDB.GetDB(application).Idao()
        repository = ItemRepo(itemDao)

    }

    fun addItem(item: Item) {
        viewModelScope.launch{repository.addItem(item)}

    }

    fun readAllItems() : LiveData<List<Item>> {
        return repository.readAllItems()
    }
}