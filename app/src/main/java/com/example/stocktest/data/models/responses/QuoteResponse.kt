package com.example.stocktest.data.models.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuoteResponse (
    @SerializedName("c")
    val current: Double,
    @SerializedName("d")
    val change: Double
) : Parcelable