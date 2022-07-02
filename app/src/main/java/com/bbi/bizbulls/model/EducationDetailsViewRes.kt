package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class EducationDetailsViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("qualication")
        @Expose
        var qualication: String? = null

        @SerializedName("institutename")
        @Expose
        var institutename: String? = null

        @SerializedName("board_uni")
        @Expose
        var boardUni: String? = null

        @SerializedName("yearofpasing")
        @Expose
        var yearofpasing: String? = null

        @SerializedName("percentage")
        @Expose
        var percentage: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}