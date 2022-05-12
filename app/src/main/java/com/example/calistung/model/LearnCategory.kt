package com.example.calistung.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LearnCategory(
    @field:SerializedName("name")
    val name:String?=null,
    @field:SerializedName("learnCourse")
    val learnCourse:ArrayList<LearnCourse>?=null
):Parcelable