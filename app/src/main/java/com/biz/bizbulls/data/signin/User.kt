package com.biz.bizbulls.data.signin


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("data")
    val `data`: Data
)