package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class AssetsRes {
    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("path")
        @Expose
        var path: String? = null

        @SerializedName("mime")
        @Expose
        var mime: String? = null

        @SerializedName("links")
        @Expose
        var links: Links? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        inner class Links {
            @SerializedName("full")
            @Expose
            var full: String? = null

            @SerializedName("thumb")
            @Expose
            var thumb: String? = null
        }
    }
}