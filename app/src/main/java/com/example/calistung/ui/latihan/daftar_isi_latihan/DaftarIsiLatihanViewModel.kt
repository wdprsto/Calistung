package com.example.calistung.ui.latihan.daftar_isi_latihan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calistung.model.TrainCourse

class DaftarIsiLatihanViewModel: ViewModel() {
    private val _trainCourses = MutableLiveData<TrainCourse>()
    val trainCourses
        get() = _trainCourses
    fun setTrainCourses(trainCourse: TrainCourse){
        _trainCourses.value=trainCourse
    }
}