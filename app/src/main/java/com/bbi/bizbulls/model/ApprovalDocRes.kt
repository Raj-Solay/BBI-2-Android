package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class ApprovalDocRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("document_id")
        @Expose
        var documentId: String? = null

        @SerializedName("document_name")
        @Expose
        var documentName: String? = null

        @SerializedName("document_type")
        @Expose
        var documentType: Any? = null

        @SerializedName("document_status")
        @Expose
        var documentStatus: String? = null
        var isApproved = false
    }
}