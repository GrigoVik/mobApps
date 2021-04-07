package com.example.app

import androidx.lifecycle.LiveData

class ItemRepo(private val idao: Idao) {

    suspend fun addItem(item: Item) {
        idao.addItem(item)
    }

    fun readAllItems() : LiveData<List<Item>>{
        return idao.readAllItems()
    }
}