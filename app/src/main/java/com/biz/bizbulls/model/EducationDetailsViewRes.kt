package com.biz.bizbulls.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class EducationDetailsViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("qualication")
        @Expose
        var qualication: String? = null

        @SerializedName("institutename")
        @Expose
        var institutename: String? = null

        @SerializedName("board_uni")
        @Expose
        var boardUni: String? = null

        @SerializedName("yearofpasing")
        @Expose
        var yearofpasing: String? = null

        @SerializedName("percentage")
        @Expose
        var percentage: String? = null

        @SerializedName("tc_course")
        @Expose
        var tc_course: String? = null

        @SerializedName("tc_institution")
        @Expose
        var tc_institution: String? = null

        @SerializedName("tc_year")
        @Expose
        var tc_year: String? = null

        @SerializedName("tc_level")
        @Expose
        var tc_level: String? = null

        @SerializedName("extra_co_activities")
        @Expose
        var extra_co_activities: String? = null

        @SerializedName("curricular_achivements")
        @Expose
        var curricular_achivements: String? = null

        @SerializedName("tc_hobbies")
        @Expose
        var tc_hobbies: String? = null

        /*----*/

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}