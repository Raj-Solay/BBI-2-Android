package com.bbi.bizbulls.data.signin


import com.google.gson.annotations.SerializedName

data class Permissions(
    @SerializedName("data")
    val `data`: List<DataXX>
)