package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class ProfessionalReferenceViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("designation")
        @Expose
        var designation: String? = null

        @SerializedName("department")
        @Expose
        var department: String? = null

        @SerializedName("organization_working")
        @Expose
        var organizationWorking: String? = null

        @SerializedName("period_contact")
        @Expose
        var periodContact: String? = null

        @SerializedName("contact_type")
        @Expose
        var contactType: String? = null

        @SerializedName("office_number")
        @Expose
        var officeNumber: String? = null

        @SerializedName("address")
        @Expose
        var address: String? = null

        @SerializedName("personal_contact")
        @Expose
        var personalContact: String? = null

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