package com.example.stocktest.data.models.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CandlesResponse (
    @SerializedName("c")
    val closePrices: List<Float>?,
    @SerializedName("s")
    val status: String
) : Parcelable