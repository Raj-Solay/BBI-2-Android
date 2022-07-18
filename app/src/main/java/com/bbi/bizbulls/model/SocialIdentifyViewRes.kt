package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class SocialIdentifyViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("aadhaar_number")
        @Expose
        var aadhaarNumber: String? = null

        @SerializedName("driving_licence_number")
        @Expose
        var drivingLicenceNumber: String? = null

        @SerializedName("pancard_number")
        @Expose
        var pancardNumber: String? = null

        @SerializedName("passport_number")
        @Expose
        var passportNumber: String? = null

        @SerializedName("voter_card_number")
        @Expose
        var voterCardNumber: String? = null

        @SerializedName("ration_card_number")
        @Expose
        var rationCardNumber: String? = null
    }
}