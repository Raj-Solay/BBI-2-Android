package com.biz.bizbulls.data.expression


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("business_place_size")
    val businessPlaceSize: String,
    @SerializedName("business_place_type")
    val businessPlaceType: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("financial_assistance")
    val financialAssistance: String,
    @SerializedName("franchisee_planning_as")
    val franchiseePlanningAs: String,
    @SerializedName("franchisee_planning_for")
    val franchiseePlanningFor: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("industry")
    val industry: String,
    @SerializedName("location_interest")
    val locationInterest: String,
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("registration_fee")
    val registrationFee: String,
    @SerializedName("updated_at")
    val updatedAt: String
)