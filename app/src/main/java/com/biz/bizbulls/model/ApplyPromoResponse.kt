package com.biz.bizbulls.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ApplyPromoResponse(
    @SerializedName("discountAmt")
    var discountAmt: Int,
    @SerializedName("payAmt")
    var payAmt: Int,
    @SerializedName("status")
    var status: String
) : Parcelable