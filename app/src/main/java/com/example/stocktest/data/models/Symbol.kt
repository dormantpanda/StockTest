package com.example.stocktest.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Symbol(
    @SerializedName("symbol")
    val symbol: String
) : Parcelable