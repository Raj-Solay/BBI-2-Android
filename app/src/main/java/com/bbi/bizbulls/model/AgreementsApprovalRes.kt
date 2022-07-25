package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class AgreementsApprovalRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("file_name")
        @Expose
        var fileName: String? = null

        @SerializedName("file_type")
        @Expose
        var fileType: String? = null
        var isApproved = false
    }
}