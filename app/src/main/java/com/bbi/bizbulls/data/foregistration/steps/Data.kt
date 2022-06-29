package com.bbi.bizbulls.data.foregistration.steps

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: String,
    @SerializedName("link_icon")
    val linkIcon: Int,
    @SerializedName("link_name")
    val linkName: String,
    @SerializedName("profile_updated_on")
    val profileUpdatedOn: String
)