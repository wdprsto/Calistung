package com.example.calistung.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LearnCourse(
    @field:SerializedName("name")
    val name:String?=null,
    @field:SerializedName("learns")
    val learns: ArrayList<Learn>?=null,
):Parcelable
