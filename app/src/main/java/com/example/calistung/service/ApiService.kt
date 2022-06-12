package com.example.calistung.service

import com.example.calistung.model.Predict
import com.example.calistung.model.ResponseCategory
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("dummy")
    suspend fun getAllData(): Response<ResponseCategory>


    @Multipart
    @Headers("Authorization: bearer bmMMM8iHjrexZYCwJg0FF0z3jQlhuk4I7UYoGEAOcSDkkOLeBMw8cy2z9uhegn82NqAAKzwoLtWTGbizxmIWEYjigP")
    @POST("predict")
    fun predictHuruf(
        //@Header("Authorization") bearer: String?,
        @Part file: MultipartBody.Part,
    ): Call<Predict>
}