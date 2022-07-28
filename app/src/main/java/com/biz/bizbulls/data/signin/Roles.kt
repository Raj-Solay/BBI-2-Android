package com.biz.bizbulls.data.signin


import com.google.gson.annotations.SerializedName

data class Roles(
    @SerializedName("data")
    val `data`: List<DataX>
)