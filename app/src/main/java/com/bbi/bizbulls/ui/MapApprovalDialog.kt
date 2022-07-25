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
import com.bbi.bizbulls.KycDocViewActivity
import com.bbi.bizbulls.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapApprovalDialog
    (var activity: KycDocViewActivity, var latLng: LatLng, var address: String) : Dialog(activity),
    OnMapReadyCallback {
    var d: Dialog? = null
    var imgZoomIn: ImageView? = null
    var imgZoomOut: ImageView? = null
    var btnCancel: TextView? = null
      lateinit var mMap: GoogleMap


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
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        imgZoomOut = findViewById<View>(R.id.imgZoomOut) as ImageView
        imgZoomIn = findViewById<View>(R.id.imgZoomIn) as ImageView
        btnCancel = findViewById<View>(R.id.btnCancel) as TextView


        val mapFragment =
            activity.supportFragmentManager.findFragmentById(R.id.mapLayout) as SupportMapFragment
        mapFragment.getMapAsync(this)

        imgZoomOut!!.setOnClickListener {
            if(this@MapApprovalDialog::mMap.isInitialized){
                cuurentZoomLevel -= 3f
                mMap.animateCamera( CameraUpdateFactory.zoomTo( cuurentZoomLevel ) )
            }
        }
        imgZoomIn!!.setOnClickListener {
            cuurentZoomLevel += 3f
            if(this@MapApprovalDialog::mMap.isInitialized){
                mMap.animateCamera( CameraUpdateFactory.zoomTo( cuurentZoomLevel ) )
            }
        }
    }
    var cuurentZoomLevel = 10f;
    override fun onMapReady(mMapTmp: GoogleMap) {
        mMap = mMapTmp
        val sydney = LatLng(latLng.latitude, latLng.longitude)
        mMapTmp.addMarker(MarkerOptions().position(sydney).title(address))
        mMapTmp.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}