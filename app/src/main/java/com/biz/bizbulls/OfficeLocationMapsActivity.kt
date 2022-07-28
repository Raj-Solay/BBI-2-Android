package com.biz.bizbulls

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.biz.bizbulls.databinding.ActivityOfficeLocationMapsBinding
import com.biz.bizbulls.model.FileUpload
import com.biz.bizbulls.remote.AddLocationResponse
import com.biz.bizbulls.remote.RetrofitClient
import com.biz.bizbulls.sharedpref.SharedPrefsManager
import com.biz.bizbulls.utils.CommonUtils
import com.biz.bizbulls.utils.MyProcessDialog
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.gson.JsonObject
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import com.yanzhenjie.album.Action
import com.yanzhenjie.album.Album
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.util.*


class OfficeLocationMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var requestBody: RequestBody
     lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityOfficeLocationMapsBinding

    var officeBluePrintPath = ""
    var officeImagePath = ""
    var officeImagePath2 = ""
    var officeImagePath3 = ""
    var officeImagePath4 = ""
    var officeImagePath5 = ""
    var officeVideoPath = ""
    var lati = ""
    var longi = ""
    var address = ""

    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@OfficeLocationMapsActivity) }
    lateinit var currentLocation: Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val REQUEST_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        binding = ActivityOfficeLocationMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapLayout) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //fetchLocation()
        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    fetchLocation();
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(p0: com.karumi.dexter.listener.PermissionRequest?,token: PermissionToken? ) {
                    token?.continuePermissionRequest()
                }

            }).check()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.mapLayout) as SupportMapFragment
//        mapFragment.getMapAsync(this)

        binding.btnUpload.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->
                    uploadDocument(File(result.get(0).path), 1)
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }
        binding.llUploadPhoto.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->

                    Glide.with(this@OfficeLocationMapsActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.imgpancard);
                    uploadDocument(File(result.get(0).path), 2)
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }
        binding.llUploadVideo.setOnClickListener {
            Album.video(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->

                    Glide.with(this@OfficeLocationMapsActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.videopancard);
                    uploadDocument(File(result.get(0).path), 3)
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }

        // adding on query listener for our search view.
        binding.idSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are getting the
                // location name from search view.
                val location: String = binding.idSearchView.getQuery().toString()

                // below line is to create a list of address
                // where we will store the list of all- address.
                var addressList: List<Address>? = null
                // checking if the entered location is null or not.
                if (location != null || location == "") {
                    // on below line we are creating and initializing a geo coder.
                    val geocoder = Geocoder(this@OfficeLocationMapsActivity)
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    // on below line we are getting the location
                    // from our list a first position.
                    if(addressList?.isNotEmpty() == true) {
                    val address1: Address? = addressList?.get(0)

                    // on below line we are creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    address1?.let {
                        val latLng = LatLng(address1.getLatitude(), address1.getLongitude())
                        lati = latLng.latitude.toString()
                        longi = latLng.longitude.toString()
                        address = location
                        if(this@OfficeLocationMapsActivity::mMap.isInitialized){
                            // on below line we are adding marker to that position.
                            mMap.addMarker(MarkerOptions().position(latLng).title(location))

                            // below line is to animate camera to that position.
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                        }

                    }
                    }

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        /*--Added--*/
        binding.llUploadPhoto2.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->

                    Glide.with(this@OfficeLocationMapsActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.imgpancard2);
                    uploadDocument(File(result.get(0).path), 4) //2
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }
        binding.llUploadPhoto3.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->

                    Glide.with(this@OfficeLocationMapsActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.imgpancard3);
                    uploadDocument(File(result.get(0).path), 5) //3
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }
        binding.llUploadPhoto4.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->

                    Glide.with(this@OfficeLocationMapsActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.imgpancard4);
                    uploadDocument(File(result.get(0).path), 6) //4
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }
        binding.llUploadPhoto5.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->

                    Glide.with(this@OfficeLocationMapsActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.imgpancard5);
                    uploadDocument(File(result.get(0).path), 7) //5
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }

        binding.imgAdd.setOnClickListener {
            if(!binding.uploadImageOne.isVisible){
                binding.uploadImageOne.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if(!binding.uploadImageTwo.isVisible){
                binding.uploadImageTwo.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if(!binding.uploadImageThree.isVisible){
                binding.uploadImageThree.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if(!binding.uploadImageFour.isVisible){
                binding.uploadImageFour.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if(!binding.uploadImageFive.isVisible){
                binding.uploadImageFive.visibility = View.VISIBLE
                binding.lnrViewMultiAdd.visibility = View.GONE
                return@setOnClickListener
            }
        }
        binding.btnSaveLocation.setOnClickListener {
            binding.btnSaveLocation.alpha = 0.5f
            val location: String = binding.idSearchView.getQuery().toString()
            sharedPrefsHelper.officeLocationBookmark = location.toString()
        }
        if(!sharedPrefsHelper.officeLocationBookmark.isNullOrEmpty()){
            binding.idSearchView.setQuery(sharedPrefsHelper.officeLocationBookmark, true)
        }

        binding.imgZoomOut.setOnClickListener {
            if(this@OfficeLocationMapsActivity::mMap.isInitialized){
                cuurentZoomLevel -= 3f
                mMap.animateCamera( CameraUpdateFactory.zoomTo( cuurentZoomLevel ) )
            }
        }
        binding.imgZoomIn.setOnClickListener {
            cuurentZoomLevel += 3f
            if(this@OfficeLocationMapsActivity::mMap.isInitialized){
                mMap.animateCamera( CameraUpdateFactory.zoomTo( cuurentZoomLevel ) )
            }
        }

        //getCurrentLOcation(this@OfficeLocationMapsActivity)

        binding.btnsubmit.setOnClickListener {
            when {
                TextUtils.isEmpty(address) -> {
                    Toast.makeText(
                        this@OfficeLocationMapsActivity,
                        getString(R.string.address_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(binding.txtOfficeSpaceMeasurement.text.toString()) -> {
                    Toast.makeText(
                        this@OfficeLocationMapsActivity,
                        getString(R.string.office_measurement_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(officeBluePrintPath) -> {
                    Toast.makeText(
                        this@OfficeLocationMapsActivity,
                        getString(R.string.path_blueprint_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(officeImagePath) -> {
                    Toast.makeText(
                        this@OfficeLocationMapsActivity,
                        getString(R.string.image_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
//                TextUtils.isEmpty(officeVideoPath) -> {
//                    Toast.makeText(
//                        this@OfficeLocationMapsActivity,
//                        getString(R.string.video_error),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
                else -> {
                    addOfficeLocation()
                }
            }
        }
    }
    var cuurentZoomLevel = 10f;
    var myCurrentLocation = ""
    lateinit var client: FusedLocationProviderClient

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        // Add a marker in Sydney and move the camera
        if(::currentLocation.isInitialized) {
            val sydney = LatLng(currentLocation.latitude, currentLocation.longitude)
          //  mMap.addMarker(MarkerOptions().position(sydney).title(address))

           // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }

    private fun uploadDocument(file: File, path: Int) {
        val uri = FileProvider.getUriForFile(
            this,
            this.packageName + ".fileProvider",
            file
        )
//        if(path ==3) {
//            requestBody = RequestBody.create("video/*".toMediaTypeOrNull(), file)
//        }else {
        requestBody =
            RequestBody.create(contentResolver.getType(uri)?.toMediaTypeOrNull(), file)
        //}
        val part: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, requestBody)

        val sharedPrefsHelper by lazy { SharedPrefsManager(this@OfficeLocationMapsActivity) }
        var call: Call<FileUpload>? = null
        call = RetrofitClient.getUrl()
            .uploadAsset(sharedPrefsHelper.authToken, part)
        MyProcessDialog.showProgressBar(this@OfficeLocationMapsActivity, 0)

        call?.enqueue(object : Callback<FileUpload> {
            override
            fun onResponse(
                call: Call<FileUpload>,
                responseObject: Response<FileUpload>
            ) {
                Log.d("ImageFullPath","Link :"+ responseObject.body()?.data?.links?.full)
                if (responseObject.code() == 201 || responseObject.code() == 200) {
                    if (path == 1) {
                        officeBluePrintPath = responseObject.body()?.data?.links?.full ?: ""
                    }
                    if (path == 2) {
                        officeImagePath = responseObject.body()?.data?.links?.full ?: ""
                    }
                    if (path == 3) {
                        officeVideoPath = responseObject.body()?.data?.links?.full ?: ""
                    }
                    if(path == 4){
                        officeImagePath2 = responseObject.body()?.data?.links?.full ?: ""
                    }
                    if(path == 5){
                        officeImagePath3 = responseObject.body()?.data?.links?.full ?: ""
                    }
                    if(path == 6){
                        officeImagePath4 = responseObject.body()?.data?.links?.full ?: ""
                    }
                    if(path == 7){
                        officeImagePath5 = responseObject.body()?.data?.links?.full ?: ""
                    }

                } else {
                    RetrofitClient.showResponseMessage(
                        this@OfficeLocationMapsActivity,
                        responseObject.code()
                    )

                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<FileUpload>, t: Throwable) {
                t.printStackTrace()
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@OfficeLocationMapsActivity, t)
            }
        })
    }

    private fun addOfficeLocation() {
        MyProcessDialog.showProgressBar(this@OfficeLocationMapsActivity, 0)
        val jsonObject = JsonObject()

        jsonObject.addProperty("location_name", address)
        jsonObject.addProperty("latitute", lati)
        jsonObject.addProperty("longitude", longi)
        jsonObject.addProperty(
            "office_measurement",
            binding.txtOfficeSpaceMeasurement.text.toString()
        )
        jsonObject.addProperty("space_blue_print_file", officeBluePrintPath)

        Log.d("ImageFullPath","officeImagePath :"+ officeImagePath)
        var ImageList = ""
        if(!officeImagePath.isNullOrEmpty()){
            ImageList += officeImagePath
        }
        if(!officeImagePath2.isNullOrEmpty()){
            ImageList += ",$officeImagePath2"
        }
        if(!officeImagePath3.isNullOrEmpty()){
            ImageList += ",$officeImagePath3"
        }
        if(!officeImagePath4.isNullOrEmpty()){
            ImageList += ",$officeImagePath4"
        }
        if(!officeImagePath5.isNullOrEmpty()){
            ImageList += ",$officeImagePath5"
        }
        Log.d("ImageFullPath","ImageList :"+ ImageList)
        jsonObject.addProperty("images", ImageList)
        jsonObject.addProperty("videos", officeVideoPath)
        val call: Call<AddLocationResponse> =
            RetrofitClient.getUrl().addOfficeLocation(sharedPrefsHelper.authToken, jsonObject)
        call.enqueue(object : Callback<AddLocationResponse> {
            override fun onResponse(
                call: Call<AddLocationResponse>,
                response: Response<AddLocationResponse>
            ) {
                if (response.isSuccessful) {
                    sharedPrefsHelper.officeLocationBookmark = ""
                    setResult(RESULT_OK)
                    finish()
                } else {
                    CommonUtils.toast(
                        this@OfficeLocationMapsActivity, getString(R.string.something_wrong_)
                    )
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<AddLocationResponse>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@OfficeLocationMapsActivity, t)
            }
        })
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener(OnSuccessListener<Location> { location ->

            if (location != null) {

                currentLocation = location
                lati = location.latitude.toString()
                longi = location.longitude.toString()

                val geocoder: Geocoder
                val addresses: List<Address>
                geocoder = Geocoder(this, Locale.getDefault())

                addresses = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                val city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                address =
                    city.plus(",").plus(state).plus(",").plus(country).plus(",").plus(postalCode)
                binding.idSearchView.setQuery(address, true)
                Log.d("MyCurrentLocation","success...not null")
               if(::mMap.isInitialized)
               {
                   val sydney = LatLng(location.latitude, location.longitude)
                   mMap.addMarker(MarkerOptions().position(sydney).title(address))

                   mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
               }
            }
        }).addOnFailureListener {
            Log.d("MyCurrentLocation","addOnFailureListener : "+ it.message)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }
}