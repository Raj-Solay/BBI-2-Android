package com.bbi.bizbulls.model


import com.google.gson.annotations.SerializedName

data class StatusData(
    @SerializedName("agreement")
    var agreement: Agreement,
    @SerializedName("business_name")
    var businessName: String,
    @SerializedName("customer_name")
    var customerName: String,
    @SerializedName("customer_status")
    var customerStatus: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("kyc")
    var kyc: Kyc,
    @SerializedName("licence")
    var licence: Licence,
    @SerializedName("location_update")
    var locationUpdate: LocationUpdate,
    @SerializedName("number")
    var number: String,
    @SerializedName("registration_fees")
    var registrationFees: RegistrationFees,
    @SerializedName("setup")
    var setup: Setup
) {
    data class Agreement(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    )

    data class Kyc(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    )

    data class Licence(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    )

    data class LocationUpdate(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    )

    data class RegistrationFees(
        @SerializedName("amount")
        var amount: Int,
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    )

    data class Setup(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    )
}