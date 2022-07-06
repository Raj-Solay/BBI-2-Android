package com.bbi.bizbulls.remote


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddLocationResponse(
    @SerializedName("data")
    var `data`: Data
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("id")
        var id: String,
        @SerializedName("images")
        var images: String,
        @SerializedName("latitute")
        var latitute: String,
        @SerializedName("location_name")
        var locationName: String,
        @SerializedName("longitude")
        var longitude: String,
        @SerializedName("office_measurement")
        var officeMeasurement: String,
        @SerializedName("space_blue_print_file")
        var spaceBluePrintFile: String,
        @SerializedName("videos")
        var videos: String
    ) : Parcelable
}