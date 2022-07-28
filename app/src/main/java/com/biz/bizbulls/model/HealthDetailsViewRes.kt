package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class HealthDetailsViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("birthidentificationmarks")
        @Expose
        var birthidentificationmarks: String? = null

        @SerializedName("birthidentificationmarks2")
        @Expose
        var birthidentificationmarks2: String? = null

        @SerializedName("handuse")
        @Expose
        var handuse: String? = null

        @SerializedName("weight")
        @Expose
        var weight: String? = null

        @SerializedName("bloodgroup")
        @Expose
        var bloodgroup: String? = null

        @SerializedName("willingtodonate")
        @Expose
        var willingtodonate: String? = null

        @SerializedName("typeofph")
        @Expose
        var typeofph: Any? = null

        @SerializedName("surgelesstreatmentundergo")
        @Expose
        var surgelesstreatmentundergo: String? = null

        @SerializedName("typeofsurgery")
        @Expose
        var typeofsurgery: String? = null

        @SerializedName("anyotherhealthissue")
        @Expose
        var anyotherhealthissue: String? = null

        @SerializedName("otherissuesdetail")
        @Expose
        var otherissuesdetail: String? = null

        @SerializedName("anyunhealthyhabit")
        @Expose
        var anyunhealthyhabit: String? = null

        @SerializedName("habbitdetails")
        @Expose
        var habbitdetails: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}