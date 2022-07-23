package com.bbi.bizbulls.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StaffMembersResponse(
    @SerializedName("data")
    var `data`: List<StaffMember>
) : Parcelable {
    @Parcelize
    data class StaffMember(
        @SerializedName("first_name")
        var firstName: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("last_name")
        var lastName: String,
        @SerializedName("mobile")
        var mobile: String,
        @SerializedName("photo")
        var photo: String,
        @SerializedName("resume")
        var resume: String,
        @SerializedName("type")
        var type: String
    ) : Parcelable
}