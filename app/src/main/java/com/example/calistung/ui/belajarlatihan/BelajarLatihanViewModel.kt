package com.example.calistung.ui.belajarlatihan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calistung.model.Category

class BelajarLatihanViewModel : ViewModel() {
    private val _category = MutableLiveData<Category>()
    val category
        get() = _category
    fun setCategory(category: Category){
        _category.value=category
    }
}