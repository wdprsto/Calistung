package com.example.calistung.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Learn(
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("gifLink")
    val gifLink: String? = null,
    @field:SerializedName("answer")
    val answer: String? = null,
    @field:SerializedName("vo")
    val vo: String? = null
):Parcelable