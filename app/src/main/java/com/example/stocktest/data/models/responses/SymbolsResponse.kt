package com.example.stocktest.data.models.responses

import android.os.Parcelable
import com.example.stocktest.data.models.Symbol
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SymbolsResponse(
    val result: List<Symbol>
) : Parcelable