package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class PersonalReferenceViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("fullname")
        @Expose
        var fullname: String? = null

        @SerializedName("relation")
        @Expose
        var relation: String? = null

        @SerializedName("sex")
        @Expose
        var sex: String? = null

        @SerializedName("age")
        @Expose
        var age: String? = null

        @SerializedName("occupation")
        @Expose
        var occupation: String? = null

        @SerializedName("location")
        @Expose
        var location: String? = null

        @SerializedName("contact_number")
        @Expose
        var contactNumber: String? = null

        @SerializedName("address")
        @Expose
        var address: String? = null
    }
}