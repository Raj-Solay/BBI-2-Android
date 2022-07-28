package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class ExpressionDetailsViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("project_name")
        @Expose
        var projectName: String? = null

        @SerializedName("industry")
        @Expose
        var industry: String? = null

        @SerializedName("location_interest")
        @Expose
        var locationInterest: String? = null

        @SerializedName("financial_assistance")
        @Expose
        var financialAssistance: String? = null

        @SerializedName("registration_fee")
        @Expose
        var registrationFee: String? = null

        @SerializedName("franchisee_planning_for")
        @Expose
        var franchiseePlanningFor: String? = null

        @SerializedName("franchisee_planning_as")
        @Expose
        var franchiseePlanningAs: String? = null

        @SerializedName("business_place_type")
        @Expose
        var businessPlaceType: String? = null

        @SerializedName("business_place_size")
        @Expose
        var businessPlaceSize: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}