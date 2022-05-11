package com.example.calistung.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LearnCourse(
    val name:String?=null,
    val learns: ArrayList<Learn>?=null,
):Parcelable
