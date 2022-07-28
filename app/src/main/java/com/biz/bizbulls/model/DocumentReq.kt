package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class DocumentReq {
    @SerializedName("document_id")
    @Expose
    var documentId: Int? = null

    @SerializedName("document_name")
    @Expose
    var documentName: String? = null

    @SerializedName("document_type")
    @Expose
    var documentType: String? = null
}