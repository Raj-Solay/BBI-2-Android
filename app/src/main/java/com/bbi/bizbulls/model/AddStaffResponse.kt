package com.bbi.bizbulls.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AddStaffResponse(
    @SerializedName("data")
    var `data`: Data
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("created_at")
        var createdAt: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("links")
        var links: Links,
        @SerializedName("mime")
        var mime: String,
        @SerializedName("path")
        var path: String,
        @SerializedName("type")
        var type: String
    ) : Parcelable {
        @Parcelize
        data class Links(
            @SerializedName("full")
            var full: String,
            @SerializedName("thumb")
            var thumb: String
        ) : Parcelable
    }
}