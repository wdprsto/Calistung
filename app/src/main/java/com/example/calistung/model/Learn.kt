package com.example.calistung.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Learn(
    val name: String? = null,
    val gifLink: String? = null,
    val answer: String? = null
):Parcelable