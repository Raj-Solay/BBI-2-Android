package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class BranchDetailsViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("bankname")
        @Expose
        var bankname: String? = null

        @SerializedName("nameinaccount")
        @Expose
        var nameinaccount: String? = null

        @SerializedName("accountnumber")
        @Expose
        var accountnumber: String? = null

        @SerializedName("branch")
        @Expose
        var branch: String? = null

        @SerializedName("ifsccode")
        @Expose
        var ifsccode: String? = null

        @SerializedName("acctype")
        @Expose
        var acctype: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}