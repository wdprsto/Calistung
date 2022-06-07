package com.example.calistung.ui.latihan.daftar_latihan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calistung.model.TrainCategory

class DaftarLatihanViewModel : ViewModel() {
    private val _trainCategories = MutableLiveData<TrainCategory>()
    val trainCategory
        get() = _trainCategories

    fun setTrainCategories(trainCategory: TrainCategory) {
        _trainCategories.value = trainCategory
    }
}