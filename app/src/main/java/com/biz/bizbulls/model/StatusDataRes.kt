package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable


class StatusDataRes : Serializable {
    @SerializedName("business_name")
    @Expose
    var businessName: String? = null

    @SerializedName("customer_name")
    @Expose
    var customerName: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("number")
    @Expose
    var number: String? = null

    @SerializedName("customer_status")
    @Expose
    var customerStatus: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("kyc")
    @Expose
    var kyc: Kyc? = null

    @SerializedName("bb_agreement")
    @Expose
    var bbAgreement: BbAgreement? = null

    @SerializedName("registration_fees")
    @Expose
    var registrationFees: RegistrationFees? = null

    @SerializedName("finability")
    @Expose
    var finability: Finability? = null

    @SerializedName("location_update")
    @Expose
    var locationUpdate: LocationUpdate? = null

    @SerializedName("agreement")
    @Expose
    var agreement: Agreement? = null

    @SerializedName("site_visit")
    @Expose
    var siteVisit: SiteVisit? = null

    @SerializedName("frenchisee_fee")
    @Expose
    var frenchiseeFee: FrenchiseeFee? = null

    @SerializedName("setup")
    @Expose
    var setup: Setup? = null

    @SerializedName("licence")
    @Expose
    var licence: Licence? = null

    class LocationUpdate : Serializable {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("date")
        @Expose
        var date: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("document_url")
        @Expose
        var documentUrl: String? = null
    }

    class Licence : Serializable {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("date")
        @Expose
        var date: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("document_url")
        @Expose
        var documentUrl: String? = null
    }

    class Setup : Serializable {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("date")
        @Expose
        var date: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("document_url")
        @Expose
        var documentUrl: String? = null
    }

    class SiteVisit : Serializable {
        @SerializedName("dates")
        @Expose
        var dates: List<String>? = null

        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("date")
        @Expose
        var date: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("document_url")
        @Expose
        var documentUrl: String? = null
    }

    class RegistrationFees : Serializable {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("date")
        @Expose
        var date: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("document_url")
        @Expose
        var documentUrl: String? = null

        @SerializedName("amount")
        @Expose
        var amount: Int? = null

        @SerializedName("gst")
        @Expose
        var gst: Int? = null

        @SerializedName("discount")
        @Expose
        var discount: Int? = null
    }

    class Status : Serializable {
        @SerializedName("kyc_status")
        @Expose
        var kycStatus: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("user_id")
        @Expose
        var userId: String? = null
    }

    class Agreement : Serializable {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("date")
        @Expose
        var date: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("document_url")
        @Expose
        var documentUrl: String? = null
    }

    class BbAgreement : Serializable {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("date")
        @Expose
        var date: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("document_url")
        @Expose
        var documentUrl: String? = null
    }

    class Doc : Serializable {
        @SerializedName("document_id")
        @Expose
        var documentId: String? = null

        @SerializedName("document_name")
        @Expose
        var documentName: String? = null

        @SerializedName("document_status")
        @Expose
        var documentStatus: String? = null
    }


    class Kyc : Serializable {
        @SerializedName("status")
        @Expose
        var status: List<Status>? = null

        @SerializedName("doc")
        @Expose
        var doc: List<Doc>? = null
    }

    class FrenchiseeFee : Serializable {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("date")
        @Expose
        var date: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("document_url")
        @Expose
        var documentUrl: String? = null

        @SerializedName("amount")
        @Expose
        var amount: Int? = null

        @SerializedName("gst")
        @Expose
        var gst: Int? = null

        @SerializedName("discount")
        @Expose
        var discount: Int? = null
    }

    class Finability : Serializable {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("date")
        @Expose
        var date: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("document_url")
        @Expose
        var documentUrl: String? = null
    }
}