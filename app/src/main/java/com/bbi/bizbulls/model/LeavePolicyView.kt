package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class LeavePolicyView {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("rotational_name")
        @Expose
        var rotationalName: String? = null

        @SerializedName("occasion")
        @Expose
        var occasion: String? = null

        @SerializedName("date_of_festival")
        @Expose
        var dateOfFestival: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}