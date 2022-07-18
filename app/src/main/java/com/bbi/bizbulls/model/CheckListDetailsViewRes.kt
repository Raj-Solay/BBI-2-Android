package com.bbi.bizbulls.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckListDetailsViewRes {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    inner class Data {

        @SerializedName("id")
        @Expose
        val id: String? = null

        @SerializedName("meet_eligibility_criteria")
        @Expose
        var meetEligibilityCriteria: String? = null

        @SerializedName("spend_required_amount")
        @Expose
        var spendRequiredAmount: Any? = null

        @SerializedName("business_registration_support")
        @Expose
        var businessRegistrationSupport: String? = null

        @SerializedName("business_loan_support")
        @Expose
        var businessLoanSupport: String? = null

        @SerializedName("accept_undergo_business_training")
        @Expose
        var acceptUndergoBusinessTraining: String? = null

        @SerializedName("accept_company_referred_setup")
        @Expose
        var acceptCompanyReferredSetup: String? = null

        @SerializedName("understood_terms_conditions")
        @Expose
        var understoodTermsConditions: String? = null

        @SerializedName("authorized_keep_contact")
        @Expose
        var authorizedKeepContact: String? = null

        @SerializedName("no_relation_clients")
        @Expose
        var noRelationClients: String? = null

        @SerializedName("your_responsibility")
        @Expose
        var yourResponsibility: String? = null
    }
}