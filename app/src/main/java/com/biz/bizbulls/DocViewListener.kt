package com.biz.bizbulls

import com.biz.bizbulls.model.AgreementsApprovalRes
import com.biz.bizbulls.model.ApprovalDocRes
import com.biz.bizbulls.model.LocationApprovalRes
import com.biz.bizbulls.model.StaffApprovalRes

interface DocViewListener {
    fun onDocView(data: ApprovalDocRes.Data?)
    fun onDocLocationView(data: LocationApprovalRes.Data?)
    fun onDocAgreementView(data: AgreementsApprovalRes.Data?)
    fun onDocSetupView(data: StaffApprovalRes.Data?)
}