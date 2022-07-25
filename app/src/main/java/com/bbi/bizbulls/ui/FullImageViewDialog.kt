package com.bbi.bizbulls.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.bbi.bizbulls.KycDocViewActivity
import com.bbi.bizbulls.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class FullImageViewDialog
    (var c: Activity) : Dialog(c){
    var d: Dialog? = null

    var btnCancel: TextView? = null
    var imgFull: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_approval_map)
        val window: Window = getWindow()!!
        val wlp = window.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        getWindow()?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )


        btnCancel = findViewById<View>(R.id.btnCancel) as TextView
        imgFull = findViewById<View>(R.id.imgFull) as ImageView

        btnCancel!!.setOnClickListener {
            dismiss()
        }

    }


}