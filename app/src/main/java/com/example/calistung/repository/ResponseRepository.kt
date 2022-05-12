package com.example.calistung.repository

import androidx.lifecycle.MutableLiveData
import com.example.calistung.model.ResponseCategory
import com.example.calistung.service.ApiService

class ResponseRepository(private val apiService: ApiService) {

    suspend fun getAllData(): MutableLiveData<ResponseCategory> {
        val liveDataTemp = MutableLiveData<ResponseCategory>()
        val responseBody = apiService.getAllData()
        liveDataTemp.value = responseBody.body()!!
        return liveDataTemp
    }
}