package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class AttachmentsViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("document_id")
        @Expose
        var documentId: Any? = null

        @SerializedName("document_name")
        @Expose
        var documentName: Any? = null

        @SerializedName("document_type")
        @Expose
        var documentType: Any? = null
    }
}