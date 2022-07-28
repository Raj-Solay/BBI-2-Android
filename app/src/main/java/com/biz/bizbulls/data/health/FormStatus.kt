package com.biz.bizbulls.data.health


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FormStatus(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DataFormStatus,
):Serializable

data class DataFormStatus(
    @SerializedName("personal")
    val personal: Int,
    @SerializedName("Healthdetail")
    val Healthdetail: Int,
    @SerializedName("UserExpressionInterest")
    val UserExpressionInterest: Int,
    @SerializedName("UserChecklist")
    val UserChecklist: Int,
    @SerializedName("Educationaldetail")
    val Educationaldetail: Int,
    @SerializedName("UserSocialIdentityDetail")
    val UserSocialIdentityDetail: Int,
    @SerializedName("Bankdetail")
    val Bankdetail: Int,
    @SerializedName("FamilyDetail")
    val FamilyDetail: Int,
    @SerializedName("Childrendetail")
    val Childrendetail: Int,
    @SerializedName("PersonalReference")
    val PersonalReference: Int,
    @SerializedName("UserDocument")
    val UserDocument: Int,
    @SerializedName("Authorization")
    val Authorization: Int
):Serializable