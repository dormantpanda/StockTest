package com.example.stocktest.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stock(
    val symbol: String,
    val price: Double?,
    val change: Double?,
    val logo: String?
) : Parcelable