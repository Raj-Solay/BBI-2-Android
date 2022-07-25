package com.bbi.bizbulls

import com.bbi.bizbulls.model.AgreementsApprovalRes
import com.bbi.bizbulls.model.ApprovalDocRes
import com.bbi.bizbulls.model.LocationApprovalRes
import com.bbi.bizbulls.model.StaffApprovalRes

interface DocViewListener {
    fun onDocView(data: ApprovalDocRes.Data?)
    fun onDocLocationView(data: LocationApprovalRes.Data?)
    fun onDocAgreementView(data: AgreementsApprovalRes.Data?)
    fun onDocSetupView(data: StaffApprovalRes.Data?)
}