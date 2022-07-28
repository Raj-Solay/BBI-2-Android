package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class CityRes {
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
        @SerializedName("city")
        @Expose
        var city: List<City>? = null

        inner class City {
            @SerializedName("id")
            @Expose
            var id: Int? = null

            @SerializedName("name")
            @Expose
            var name: String? = null

            @SerializedName("state_id")
            @Expose
            var stateId: String? = null

            @SerializedName("created_on")
            @Expose
            var createdOn: String? = null

            @SerializedName("status")
            @Expose
            var status: String? = null
        }
    }
}