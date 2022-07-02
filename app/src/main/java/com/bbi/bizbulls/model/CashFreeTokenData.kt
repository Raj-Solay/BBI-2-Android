package com.bbi.bizbulls.model


import com.google.gson.annotations.SerializedName

data class CashFreeTokenData(
    @SerializedName("cf_order_id")
    var cfOrderId: Int,
    @SerializedName("customer_details")
    var customerDetails: CustomerDetails,
    @SerializedName("entity")
    var entity: String,
    @SerializedName("order_amount")
    var orderAmount: Double,
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
    @SerializedName("order_status")
    var orderStatus: String,
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
) {
    data class CustomerDetails(
        @SerializedName("customer_email")
        var customerEmail: String,
        @SerializedName("customer_id")
        var customerId: String,
        @SerializedName("customer_name")
        var customerName: String,
        @SerializedName("customer_phone")
        var customerPhone: String
    )

    data class OrderMeta(
        @SerializedName("notify_url")
        var notifyUrl: String,
        @SerializedName("payment_methods")
        var paymentMethods: Any,
        @SerializedName("return_url")
        var returnUrl: String
    )

    data class Payments(
        @SerializedName("url")
        var url: String
    )

    data class Refunds(
        @SerializedName("url")
        var url: String
    )

    data class Settlements(
        @SerializedName("url")
        var url: String
    )
}