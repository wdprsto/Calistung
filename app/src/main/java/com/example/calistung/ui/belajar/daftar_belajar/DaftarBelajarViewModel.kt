package com.example.calistung.ui.belajar.daftar_belajar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calistung.model.LearnCategory

class DaftarBelajarViewModel : ViewModel() {
    private val _learnCategories = MutableLiveData<LearnCategory>()
    val learnCategory
        get() = _learnCategories
//    init {
//        _learnCategories.value=dummy.learnCategories
//    }
    fun setLearnCategories(learnCategory: LearnCategory){
        _learnCategories.value=learnCategory
    }
}