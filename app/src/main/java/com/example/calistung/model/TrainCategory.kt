package com.example.calistung.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrainCategory(
    @field:SerializedName("name")
    val name:String?=null,
    @field:SerializedName("trainCourse")
    val trainCourse: ArrayList<TrainCourse>?=null
): Parcelable
