package com.bbi.bizbulls.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
public data class StatusData(
    @SerializedName("agreement")
    var agreement: Agreement,
    @SerializedName("bb_agreement")
    var bbAgreement: BbAgreement,
    @SerializedName("business_name")
    var businessName: String,
    @SerializedName("customer_name")
    var customerName: String,
    @SerializedName("customer_status")
    var customerStatus: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("finability")
    var finability: Finability,
    @SerializedName("frenchisee_fee")
    var frenchiseeFee: FrenchiseeFee,
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
    var setup: Setup,
    @SerializedName("site_visit")
    var siteVisit: SiteVisit,
    @SerializedName("type")
    var type: String,
    @SerializedName("is_customer")
    var isCustomer: Boolean
) : Parcelable {
    @Parcelize
    data class Agreement(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable

    @Parcelize
    data class BbAgreement(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable

    @Parcelize
    data class Finability(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable

    @Parcelize
    data class FrenchiseeFee(
        @SerializedName("amount")
        var amount: Int,
        @SerializedName("date")
        var date: String,
        @SerializedName("discount")
        var discount: Int,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("gst")
        var gst: Int,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable

    @Parcelize
    data class Kyc(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable

    @Parcelize
    data class Licence(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable

    @Parcelize
    data class LocationUpdate(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable

    @Parcelize
    data class RegistrationFees(
        @SerializedName("amount")
        var amount: Int,
        @SerializedName("date")
        var date: String,
        @SerializedName("discount")
        var discount: Int,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("gst")
        var gst: Int,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable

    @Parcelize
    data class Setup(
        @SerializedName("date")
        var date: String,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable

    @Parcelize
    data class SiteVisit(
        @SerializedName("date")
        var date: String,
        @SerializedName("dates")
        var dates: List<String>,
        @SerializedName("document_url")
        var documentUrl: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("status")
        var status: String
    ) : Parcelable
}