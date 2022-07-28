package com.biz.bizbulls.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DatesResponse(
    @SerializedName("dates")
    var dates: List<String>,
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Int
) : Parcelable