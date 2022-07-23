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


class FileSeletionDialog
    (var c: Activity) : Dialog(c) {
    var d: Dialog? = null
    var txtImage: TextView? = null
    var txtPdf: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_file_selection)
        val window: Window = getWindow()!!
        val wlp = window.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        getWindow()?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        txtImage = findViewById<View>(R.id.txtImage) as TextView
        txtPdf = findViewById<View>(R.id.txtPdf) as TextView
    }
}