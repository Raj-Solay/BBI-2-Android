package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class FamilyDetailsViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("relation")
        @Expose
        var relation: String? = null

        @SerializedName("age")
        @Expose
        var age: String? = null

        @SerializedName("education")
        @Expose
        var education: String? = null

        @SerializedName("occupation")
        @Expose
        var occupation: String? = null

        @SerializedName("monthlyincome")
        @Expose
        var monthlyincome: String? = null

        @SerializedName("contactno")
        @Expose
        var contactno: String? = null

        @SerializedName("address")
        @Expose
        var address: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}