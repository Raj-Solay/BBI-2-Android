package com.biz.bizbulls

import com.biz.bizbulls.model.*

interface DocViewListener {
    fun onDocView(data: ApprovalDocRes.Data?, isVerifyEvent : Boolean)
    fun onDocLocationView(data: LocationApprovalRes.Data?, isVerifyEvent : Boolean)
    fun onDocAgreementView(data: AgreementsApprovalRes.Data?, isVerifyEvent : Boolean)
    fun onDocSetupView(data: StaffApprovalRes.Data?, isVerifyEvent : Boolean)
    fun onLicenseView(data: LicenseApprovalRes.Data?, isVerifyEvent : Boolean)
}