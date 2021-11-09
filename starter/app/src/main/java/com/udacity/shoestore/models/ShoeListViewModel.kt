package com.udacity.shoestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeListViewModel : ViewModel() {
    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    fun createShoe(name: String, size: Double, company: String, description: String, images: List<String> = mutableListOf()) {
        val shoe = Shoe(name, size, company, description, images)
        _shoeList.value?.add(shoe)
    }

}