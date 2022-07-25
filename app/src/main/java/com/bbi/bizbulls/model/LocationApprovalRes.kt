package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class LocationApprovalRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("location_name")
        @Expose
        var locationName: String? = null

        @SerializedName("latitute")
        @Expose
        var latitute: String? = null

        @SerializedName("longitude")
        @Expose
        var longitude: String? = null

        @SerializedName("office_measurement")
        @Expose
        var officeMeasurement: String? = null

        @SerializedName("space_blue_print_file")
        @Expose
        var spaceBluePrintFile: String? = null

        @SerializedName("images")
        @Expose
        var images: String? = null

        @SerializedName("videos")
        @Expose
        var videos: String? = null
        var isApproved = false
    }
}