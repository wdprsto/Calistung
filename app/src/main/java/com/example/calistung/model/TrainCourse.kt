package com.example.calistung.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrainCourse(
    val name:String?=null,
    val trains: ArrayList<Train>?=null
): Parcelable
