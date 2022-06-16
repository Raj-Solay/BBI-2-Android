package com.bbi.bizbulls.data.expression


import com.google.gson.annotations.SerializedName

data class ExpressionOfInterest(
    @SerializedName("data")
    val `data`: Data
)