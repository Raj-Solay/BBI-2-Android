package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class StaffApprovalRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("first_name")
        @Expose
        var firstName: String? = null

        @SerializedName("last_name")
        @Expose
        var lastName: String? = null

        @SerializedName("mobile")
        @Expose
        var mobile: String? = null

        @SerializedName("type")
        @Expose
        var type: Any? = null

        @SerializedName("photo")
        @Expose
        var photo: String? = null

        @SerializedName("resume")
        @Expose
        var resume: String? = null
        var isApproved = false
    }
}