package com.example.calistung.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrainQuestion(
    @field:SerializedName("name")
    val name:String?=null,
    @field:SerializedName("trains")
    val trains: ArrayList<Train>?=null
):Parcelable