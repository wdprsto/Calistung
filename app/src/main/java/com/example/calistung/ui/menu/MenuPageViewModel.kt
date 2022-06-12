package com.example.calistung.ui.menu

import androidx.lifecycle.ViewModel
import com.example.calistung.repository.ResponseRepository

class MenuPageViewModel(private val responseRepository: ResponseRepository) : ViewModel() {

    suspend fun getAllData() = responseRepository.getAllData()

}