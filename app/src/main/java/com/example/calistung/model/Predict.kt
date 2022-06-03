package com.example.calistung.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Predict(
	@field:SerializedName("result_predict")
	val resultPredict: String
) : Parcelable
