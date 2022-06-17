package com.bbi.bizbulls.data.signin


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: Any,
    @SerializedName("profile_pic")
    val profilePic: Any,
    @SerializedName("roles")
    val roles: Roles,
    @SerializedName("updated_at")
    val updatedAt: String
)