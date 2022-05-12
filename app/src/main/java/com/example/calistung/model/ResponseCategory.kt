package com.example.calistung.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseCategory(
    @field:SerializedName("huruf")
    val huruf: Category? = null,
    @field:SerializedName("angka")
    val angka: Category? = null,
    @field:SerializedName("hitung")
    val hitung: Category? = null
) : Parcelable
