package com.bbi.bizbulls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bbi.bizbulls.databinding.ActivityLocationApprovalViewBinding
import com.bbi.bizbulls.model.ApprovalDocRes
import com.bbi.bizbulls.model.LocationApprovalRes
import com.bbi.bizbulls.model.PersonalUserAll
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.DialogDocApprove
import com.bbi.bizbulls.utils.MyProcessDialog
import com.foldio.android.adapter.LocationApprovalAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationDocViewActivity : AppCompatActivity(),DocViewListener , OnMapReadyCallback {
    lateinit var binding:ActivityLocationApprovalViewBinding
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@LocationDocViewActivity) }

    var lat = ""
    var log = ""
    var address = ""
    lateinit var mMap: GoogleMap

    var cuurentZoomLevel = 10f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLocationApprovalViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lat = intent.getStringExtra("LAT").toString()
        log = intent.getStringExtra("LOG").toString()
        address = intent.getStringExtra("ADDRESS").toString()

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapLayout) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnCancel.setOnClickListener { onBackPressed() }
        binding.imgZoomOut!!.setOnClickListener {
            if(this@LocationDocViewActivity::mMap.isInitialized){
                cuurentZoomLevel -= 3f
                mMap.animateCamera( CameraUpdateFactory.zoomTo( cuurentZoomLevel ) )
            }
        }
        binding.imgZoomIn!!.setOnClickListener {
            cuurentZoomLevel += 3f
            if(this@LocationDocViewActivity::mMap.isInitialized){
                mMap.animateCamera( CameraUpdateFactory.zoomTo( cuurentZoomLevel ) )
            }
        }
    }

    override fun onDocView(data: ApprovalDocRes.Data?) {

    }

    override fun onMapReady(mMapTmp: GoogleMap) {
        mMap = mMapTmp
        mMapTmp.mapType = GoogleMap.MAP_TYPE_SATELLITE
        val sydney = LatLng(lat.toDouble(), log.toDouble())
        mMapTmp.addMarker(MarkerOptions().position(sydney).title(address))
        mMapTmp.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}