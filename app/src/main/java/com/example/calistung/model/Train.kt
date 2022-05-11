package com.example.calistung.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Train(
    val name:String?=null,
    val question:String?=null,
    val answer:String?=null
): Parcelable
