package com.bbi.bizbulls.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CashFreeTokenData(
    @SerializedName("pg")
    var pg: Pg,
    @SerializedName("response")
    var response: Response
) : Parcelable {
    @Parcelize
    data class Pg(
        @SerializedName("cf_order_id")
        var cfOrderId: Int,
        @SerializedName("customer_details")
        var customerDetails: CustomerDetails,
        @SerializedName("entity")
        var entity: String,
        @SerializedName("order_amount")
        var orderAmount: Int,
        @SerializedName("order_currency")
        var orderCurrency: String,
        @SerializedName("order_expiry_time")
        var orderExpiryTime: String,
        @SerializedName("order_id")
        var orderId: String,
        @SerializedName("order_meta")
        var orderMeta: OrderMeta,
        @SerializedName("order_note")
        var orderNote: String,
        @SerializedName("order_splits")
        var orderSplits: List<String>,
        @SerializedName("order_status")
        var orderStatus: String,
        @SerializedName("order_tags")
        var orderTags: String,
        @SerializedName("order_token")
        var orderToken: String,
        @SerializedName("payment_link")
        var paymentLink: String,
        @SerializedName("payments")
        var payments: Payments,
        @SerializedName("refunds")
        var refunds: Refunds,
        @SerializedName("settlements")
        var settlements: Settlements
    ) : Parcelable {
        @Parcelize
        data class CustomerDetails(
            @SerializedName("customer_email")
            var customerEmail: String,
            @SerializedName("customer_id")
            var customerId: String,
            @SerializedName("customer_name")
            var customerName: String,
            @SerializedName("customer_phone")
            var customerPhone: String
        ) : Parcelable

        @Parcelize
        data class OrderMeta(
            @SerializedName("notify_url")
            var notifyUrl: String,
            @SerializedName("payment_methods")
            var paymentMethods: String,
            @SerializedName("return_url")
            var returnUrl: String
        ) : Parcelable

        @Parcelize
        data class Payments(
            @SerializedName("url")
            var url: String
        ) : Parcelable

        @Parcelize
        data class Refunds(
            @SerializedName("url")
            var url: String
        ) : Parcelable

        @Parcelize
        data class Settlements(
            @SerializedName("url")
            var url: String
        ) : Parcelable
    }

    @Parcelize
    data class Response(
        @SerializedName("created_at")
        var createdAt: String,
        @SerializedName("customer_email")
        var customerEmail: String,
        @SerializedName("customer_name")
        var customerName: String,
        @SerializedName("customer_phone")
        var customerPhone: String,
        @SerializedName("id")
        var id: Int,
        @SerializedName("order_amount")
        var orderAmount: Int,
        @SerializedName("order_currency")
        var orderCurrency: String,
        @SerializedName("order_id")
        var orderId: String,
        @SerializedName("response")
        var response: String,
        @SerializedName("status")
        var status: String,
        @SerializedName("updated_at")
        var updatedAt: String,
        @SerializedName("user_id")
        var userId: Int,
        @SerializedName("uuid")
        var uuid: String
    ) : Parcelable
}