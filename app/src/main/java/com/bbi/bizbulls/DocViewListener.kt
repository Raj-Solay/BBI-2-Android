package com.bbi.bizbulls

import com.bbi.bizbulls.model.ApprovalDocRes

interface DocViewListener {
    fun onDocView(data: ApprovalDocRes.Data?)
}