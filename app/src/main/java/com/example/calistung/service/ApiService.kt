package com.example.calistung.service

import com.example.calistung.model.Predict
import com.example.calistung.model.ResponseCategory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("raw")
    suspend fun getAllData(): Response<ResponseCategory>

    @Multipart
    @POST("/predict")
    fun predictHuruf(
//        @Header("Authorization") bearer: String?,
        @Part file: MultipartBody.Part,
    ): Call<Predict>
}