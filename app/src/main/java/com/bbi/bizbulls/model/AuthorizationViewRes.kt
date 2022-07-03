package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class AuthorizationViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("authorization")
        @Expose
        var authorization: String? = null

        @SerializedName("ip_address")
        @Expose
        var ipAddress: String? = null
    }
}