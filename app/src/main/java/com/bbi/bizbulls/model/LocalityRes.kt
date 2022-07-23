package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.bbi.bizbulls.model.LocalityRes.Data.Locality

class LocalityRes {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("locality")
        @Expose
        var locality: List<Locality>? = null

        inner class Locality {
            @SerializedName("id")
            @Expose
            var id: Int? = null

            @SerializedName("name")
            @Expose
            var name: String? = null

            @SerializedName("state_id")
            @Expose
            var stateId: String? = null

            @SerializedName("city_id")
            @Expose
            var cityId: String? = null

            @SerializedName("created_on")
            @Expose
            var createdOn: String? = null

            @SerializedName("status")
            @Expose
            var status: String? = null
        }
    }
}