package com.example.stocktest.data.models.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("logo")
    val logo: String,
) : Parcelable