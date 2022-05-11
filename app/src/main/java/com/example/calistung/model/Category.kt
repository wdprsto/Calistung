package com.example.calistung.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val name:String?=null,
    val subCategories: ArrayList<SubCategory>?=null
):Parcelable
