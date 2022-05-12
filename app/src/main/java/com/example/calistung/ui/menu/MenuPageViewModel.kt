package com.example.calistung.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calistung.model.ResponseCategory
import com.example.calistung.repository.ResponseRepository
import com.example.calistung.utils.Dummy

class MenuPageViewModel(private val responseRepository: ResponseRepository) : ViewModel() {
    private val _responseCategory = MutableLiveData<ResponseCategory>()
    val responseCategory
        get() = _responseCategory

    init {
//        setResponseCategory(Dummy.response)
    }

    suspend fun getAllData() = responseRepository.getAllData()

    fun setResponseCategory(responseCategory: ResponseCategory) {
        _responseCategory.value = responseCategory
    }

}