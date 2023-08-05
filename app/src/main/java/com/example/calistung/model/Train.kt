package com.example.calistung.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Train(
    @field:SerializedName("name")
    val name:String?=null,
    @field:SerializedName("question")
    val question:String?=null,
    @field:SerializedName("answer")
    val answer:String?=null,
    @field:SerializedName("vo")
    val vo: String? = null
): Parcelable
