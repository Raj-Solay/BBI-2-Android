package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class PersonalDetailsViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("user_id")
        @Expose
        var userId: String? = null

        @SerializedName("present_acco_since")
        @Expose
        var presentAccoSince: String? = null

        @SerializedName("fathersname")
        @Expose
        var fathersname: String? = null

        @SerializedName("permanent_emergency_no")
        @Expose
        var permanentEmergencyNo: String? = null

        @SerializedName("present_emergency_no")
        @Expose
        var presentEmergencyNo: String? = null

        @SerializedName("dob")
        @Expose
        var dob: String? = null

        @SerializedName("mothersname")
        @Expose
        var mothersname: String? = null

        @SerializedName("permanent_acco_since")
        @Expose
        var permanentAccoSince: String? = null

        @SerializedName("state")
        @Expose
        var state: String? = null

        @SerializedName("gender")
        @Expose
        var gender: String? = null

        @SerializedName("permanent_acco_type")
        @Expose
        var permanentAccoType: String? = null

        @SerializedName("mothertongue")
        @Expose
        var mothertongue: String? = null

        @SerializedName("present_acco_type")
        @Expose
        var presentAccoType: String? = null

        @SerializedName("maritalstatus")
        @Expose
        var maritalstatus: String? = null

        @SerializedName("present_add")
        @Expose
        var presentAdd: String? = null

        @SerializedName("whatsappnumber")
        @Expose
        var whatsappnumber: String? = null

        @SerializedName("pob")
        @Expose
        var pob: String? = null

        @SerializedName("alternatenumber")
        @Expose
        var alternatenumber: String? = null

        @SerializedName("zip")
        @Expose
        var zip: String? = null

        @SerializedName("permanent_add")
        @Expose
        var permanentAdd: String? = null

        @SerializedName("fullname")
        @Expose
        var fullname: String? = null

        @SerializedName("emailid")
        @Expose
        var emailid: String? = null

        @SerializedName("age")
        @Expose
        var age: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}