package com.biz.bizbulls.data.signin


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("permissions")
    val permissions: Permissions,
    @SerializedName("updated_at")
    val updatedAt: String
)