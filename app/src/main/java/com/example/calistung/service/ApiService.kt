package com.example.calistung.service

import com.example.calistung.model.ResponseCategory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("raw")
    suspend fun getAllData(): Response<ResponseCategory>
}