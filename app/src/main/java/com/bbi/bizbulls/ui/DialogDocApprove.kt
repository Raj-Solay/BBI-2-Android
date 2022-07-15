package com.bbi.bizbulls.ui

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.bbi.bizbulls.R


class DialogDocApprove
    (var c: Activity) : Dialog(c) {
    var d: Dialog? = null
    var btnCancel: TextView? = null
    var btnApproval: TextView? = null
    var imdDocView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_approval_doc)
        val window: Window = getWindow()!!
        val wlp = window.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        getWindow()?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        btnCancel = findViewById<View>(R.id.btnCancel) as TextView
        btnApproval = findViewById<View>(R.id.btnApproval) as TextView
        imdDocView = findViewById<View>(R.id.docViewDialog) as ImageView
    }
}