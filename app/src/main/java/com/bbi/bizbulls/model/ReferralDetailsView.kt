package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class ReferralDetailsView {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("name")
        @Expose
        var name: Any? = null

        @SerializedName("epm_code")
        @Expose
        var epmCode: String? = null

        @SerializedName("designation")
        @Expose
        var designation: String? = null

        @SerializedName("department")
        @Expose
        var department: String? = null

        @SerializedName("company")
        @Expose
        var company: String? = null

        @SerializedName("period_of_work")
        @Expose
        var periodOfWork: String? = null

        @SerializedName("reporting_to")
        @Expose
        var reportingTo: String? = null

        @SerializedName("work_location")
        @Expose
        var workLocation: String? = null

        @SerializedName("how_do_youknow")
        @Expose
        var howDoYouknow: String? = null

        @SerializedName("contact_no")
        @Expose
        var contactNo: String? = null

        @SerializedName("mail_id")
        @Expose
        var mailId: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}