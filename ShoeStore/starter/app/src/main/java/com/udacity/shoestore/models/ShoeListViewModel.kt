package com.udacity.shoestore.models

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoeListViewModel : ViewModel() {
    private val _shoeList = MutableLiveData<List<Shoe>>()
    val shoeList: LiveData<List<Shoe>>
        get() = _shoeList

    fun createShoe(name : String, size : String, company : String, description : String) {
        val shoe = Shoe(name, size.toDouble(), company, description, emptyList())
        val newList = shoeList.value?.toMutableList() ?: mutableListOf()
        newList.add(shoe)
        _shoeList.value = newList
    }
}