package com.example.calistung.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calistung.repository.ResponseRepository
import com.example.calistung.ui.menu.MenuPageViewModel

class ViewModelFactory(
    private val responseRepository: ResponseRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MenuPageViewModel::class.java) -> {
                MenuPageViewModel(responseRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
