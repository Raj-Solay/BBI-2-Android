package com.bbi.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class PersonalUserAll {
    @SerializedName("data")
    @Expose
    var data: ArrayList<Data>? = null

    inner class Data : Serializable{
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("user_id")
        @Expose
        var userId: String? = null

        @SerializedName("present_acco_since")
        @Expose
        var presentAccoSince: Any? = null

        @SerializedName("fathersname")
        @Expose
        var fathersname: String? = null

        @SerializedName("permanent_emergency_no")
        @Expose
        var permanentEmergencyNo: Any? = null

        @SerializedName("present_emergency_no")
        @Expose
        var presentEmergencyNo: Any? = null

        @SerializedName("dob")
        @Expose
        var dob: String? = null

        @SerializedName("mothersname")
        @Expose
        var mothersname: String? = null

        @SerializedName("permanent_acco_since")
        @Expose
        var permanentAccoSince: Any? = null

        @SerializedName("state")
        @Expose
        var state: Any? = null

        @SerializedName("gender")
        @Expose
        var gender: String? = null

        @SerializedName("permanent_acco_type")
        @Expose
        var permanentAccoType: String? = null

        @SerializedName("mothertongue")
        @Expose
        var mothertongue: Any? = null

        @SerializedName("present_acco_type")
        @Expose
        var presentAccoType: String? = null

        @SerializedName("maritalstatus")
        @Expose
        var maritalstatus: String? = null

        @SerializedName("present_add")
        @Expose
        var presentAdd: Any? = null

        @SerializedName("whatsappnumber")
        @Expose
        var whatsappnumber: Any? = null

        @SerializedName("pob")
        @Expose
        var pob: String? = null

        @SerializedName("alternatenumber")
        @Expose
        var alternatenumber: Any? = null

        @SerializedName("zip")
        @Expose
        var zip: Any? = null

        @SerializedName("permanent_add")
        @Expose
        var permanentAdd: Any? = null

        @SerializedName("fullname")
        @Expose
        var fullname: String? = null

        @SerializedName("emailid")
        @Expose
        var emailid: Any? = null

        @SerializedName("age")
        @Expose
        var age: String? = null

        @SerializedName("role_id")
        @Expose
        var roleId: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}