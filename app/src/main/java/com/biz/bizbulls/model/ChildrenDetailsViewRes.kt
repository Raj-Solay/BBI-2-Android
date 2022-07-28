package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class ChildrenDetailsViewRes {
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

        @SerializedName("age")
        @Expose
        var age: String? = null

        @SerializedName("gender")
        @Expose
        var gender: String? = null

        @SerializedName("stayawith")
        @Expose
        var stayawith: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}