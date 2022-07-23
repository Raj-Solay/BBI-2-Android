package com.bbi.bizbulls

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.data.health.FormStatus
import com.bbi.bizbulls.databinding.ActivityDashboardBinding
import com.bbi.bizbulls.menu.*
import com.bbi.bizbulls.model.UserDetails
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.fragment.CustomerFOStatusFragment
import com.bbi.bizbulls.ui.fragment.HomeCustomerFragment
import com.bbi.bizbulls.ui.registrationforfo.FoRegistrationDashBoardActivity
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.Globals
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@DashboardActivity) }
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    var binding: ActivityDashboardBinding? = null
    var myAccount: ConstraintLayout? = null
    var myRefer: ConstraintLayout? = null
    var myOffer: ConstraintLayout? = null
    var myWallet: ConstraintLayout? = null
    var layoutcp : ConstraintLayout? = null
    var about: ConstraintLayout? = null
    var termsAndCondition: ConstraintLayout? = null
    var privacyPolicy: ConstraintLayout? = null
    var docApprovel: ConstraintLayout? = null
    var registerComplaint: ConstraintLayout? = null
    var contactUs: ConstraintLayout? = null
    var followUs: ConstraintLayout? = null
    var rateUs: ConstraintLayout? = null
    var settings: ConstraintLayout? = null
    var logout: ConstraintLayout? = null
    var layoutnotification: RelativeLayout?=null
    var layoutsearch: RelativeLayout?=null
    var layouthelp:RelativeLayout?=null
    var count = 0
    lateinit var nav_user:TextView
    lateinit var nav_mobile:TextView
    lateinit var nav_email:TextView
    var homeCustomerFragment: HomeCustomerFragment? = null
    var customerFOStatusFragment: CustomerFOStatusFragment? = null
    lateinit var client: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        hideKeyboard(this)
        init()
       // if(!CommonUtils.isRedirectToStatus){
            getUserRole(this)
     //   }
       /* println("________NAME ::${sharedPrefsHelper.userName}")
        println("________EMAIL  ::${sharedPrefsHelper.email}")
        println("________PHONE  ::${sharedPrefsHelper.phone}")
        println("________TOKEN_ID  ::${sharedPrefsHelper.tokenID}")
        println("________AUTH_TOKEN  ::${sharedPrefsHelper.authToken}")*/

    }



    override fun onResume() {
        super.onResume()
        if(CommonUtils.isRedirectToStatus){
            CommonUtils.isRedirectToStatus = false
            supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, customerFOStatusFragment!!).commit()
        }
    }

    var isFormStatusComplted = false
    fun getFormStatus(){
          //  MyProcessDialog.showProgressBar(this@DashboardActivity, 0)
            var token:String=sharedPrefsHelper.authToken
            val call = RetrofitClient.getUrl().formStatus(token)
            call?.enqueue(object : Callback<FormStatus> {
                override
                fun onResponse(
                    call: Call<FormStatus>,
                    responseObject: Response<FormStatus>
                ) {
                    MyProcessDialog.dismiss()
                    if (responseObject.code() == 200 || responseObject.code() == 201) {
                        if (responseObject.body()?.data!= null) {
                            var isCompleted = 0
                            if(responseObject.body()!!.data.personal >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.Healthdetail >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.UserExpressionInterest >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.UserChecklist >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.Educationaldetail >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.UserSocialIdentityDetail >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.Bankdetail >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.FamilyDetail >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.Childrendetail >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.PersonalReference >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.UserDocument >= 1){
                                isCompleted++
                            }
                            if(responseObject.body()!!.data.Authorization  >= 1){
                                isCompleted++
                            }

                            isFormStatusComplted = isCompleted == 12
                            sharedPrefsHelper.isFormCompleted = isFormStatusComplted
                            var progress = (isCompleted * 8.33333333)
                            if(progress > 90){
                                progress = 100.0
                            }
                            if(linearProgressbar != null){
                                linearProgressbar!!.progress = progress.toInt()
                            }
                            MyProcessDialog.dismiss()
                        }
                    } else {
                        RetrofitClient.showResponseMessage(this@DashboardActivity, responseObject.code())

                    }
                    MyProcessDialog.dismiss()
                }

                override
                fun onFailure(call: Call<FormStatus>, t: Throwable) {
                    MyProcessDialog.dismiss()
                    RetrofitClient.showFailedMessage(this@DashboardActivity, t)
                }
            })

    }

    fun getUserRole(activity: DashboardActivity) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(this) }

        val call: Call<UserDetails> =
            RetrofitClient.getUrl().userRoleDetails(sharedPrefsHelper.authToken)
        println("________URL ::${call.request().url}")
        println("________authToken ::${sharedPrefsHelper.authToken}")
        var token = sharedPrefsHelper.authToken;

       // if(CommonUtils.isHideProgress){
            CommonUtils.isHideProgress = false
            MyProcessDialog.showProgressBar(activity, 0)
      //  }
        call.enqueue(object : Callback<UserDetails> {
            override
            fun onResponse(
                call: Call<UserDetails>,
                responseObject: Response<UserDetails>
            ) {
                if (responseObject.isSuccessful) {
                    //responseObject.body()?.data
                    var userDetails = responseObject.body()
                  //  Log.d("UserDetails","Role : " + userDetails?.data?.id)
                 //   Log.d("UserDetails","Role : " + userDetails?.data?.roleId)
                    sharedPrefsHelper.userName = userDetails?.data?.name.toString()
                    sharedPrefsHelper.role = userDetails?.data?.roleId.toString()
                    sharedPrefsHelper.userId = userDetails?.data?.id.toString()
                    sharedPrefsHelper.phone = userDetails?.data?.phone.toString()
                    sharedPrefsHelper.email = userDetails?.data?.email.toString()
                    sharedPrefsHelper.userName = userDetails?.data?.name.toString()
                    nav_user.text=userDetails?.data?.name.toString()
                     nav_mobile.text=userDetails?.data?.phone.toString()
                     nav_email.text=userDetails?.data?.email.toString()
                    try{
                        if(userDetails?.data?.roleId!= null &&
                            userDetails?.data?.roleId!!.toInt() == Globals.USER_TYPE_FO_TEAM){
                            docApprovel!!.visibility = View.VISIBLE
                        }else{
                            docApprovel!!.visibility = View.GONE
                        }
                    }catch (e  :Exception){
                        docApprovel!!.visibility = View.GONE
                    }
                    val menu = binding!!.bottomNavigationView.menu

                    if(sharedPrefsHelper.role.toInt() == Globals.USER_TYPE_FM){
                        menu.findItem(R.id.navigation_fohome).isVisible = true
                        menu.findItem(R.id.navigation_forevenue).isVisible = true
                        getFormStatus()
                    }else{
                        menu.findItem(R.id.navigation_forevenue).isVisible = false
                        menu.findItem(R.id.navigation_fohome).isVisible = false
                    }

                } else {
                    RetrofitClient.showResponseMessage(activity, responseObject.code())
                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<UserDetails>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(activity, t)
            }
        })
    }
     var  linearProgressbar : LinearProgressIndicator? = null
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    fun init() {



        binding!!.bottomNavigationView.background = null
        binding!!.bottomNavigationView.setOnItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.navigation_fohome -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, homeCustomerFragment!!).commit()

                }
                R.id.navigation_forevenue ->{
                    if(!isFormStatusComplted){
                        Toast.makeText(this, "Please Completed the Registration Forms First.", Toast.LENGTH_LONG).show()
                    }else{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.flFragment, customerFOStatusFragment!!).commit()
                    }

                }
            }
            true
        }
        val menu = binding!!.bottomNavigationView.menu
        if(sharedPrefsHelper.role.toInt() == Globals.USER_TYPE_EMPLOYEE){
            menu.findItem(R.id.navigation_forevenue).isVisible = false
            menu.findItem(R.id.navigation_fohome).isVisible = false
        }else{
            menu.findItem(R.id.navigation_fohome).isVisible = true
            menu.findItem(R.id.navigation_forevenue).isVisible = true
        }
        binding!!.navfo.bringToFront()
        val hView = binding!!.navfo.getHeaderView(0)
        linearProgressbar = hView.findViewById<LinearProgressIndicator>(R.id.progressBarprofileupdate)
        linearProgressbar!!.progress = 0
        nav_user= hView.findViewById<AppCompatTextView>(R.id.txtfoname)
        nav_user.text = sharedPrefsHelper.userName
        nav_mobile = hView.findViewById<AppCompatTextView>(R.id.txtfomobile)
        nav_mobile.text = sharedPrefsHelper.phone
        nav_email = hView.findViewById<AppCompatTextView>(R.id.txtfoemail)
        nav_email.text = sharedPrefsHelper.email
        myAccount = hView.findViewById(R.id.myAccount)
        myAccount?.setOnClickListener(this)
        myRefer = hView.findViewById(R.id.myRefer)
        myRefer?.setOnClickListener(this)
        myOffer = hView.findViewById(R.id.myOffer)
        myOffer?.setOnClickListener(this)
        docApprovel = hView.findViewById(R.id.docApprovel)
        docApprovel?.setOnClickListener(this)
        myWallet = hView.findViewById(R.id.myWallet)
        myWallet?.setOnClickListener(this)
        about = hView.findViewById(R.id.about)
        about?.setOnClickListener(this)
        termsAndCondition = hView.findViewById(R.id.termsAndCondition)
        termsAndCondition?.setOnClickListener(this)
        privacyPolicy = hView.findViewById(R.id.privacyPolicy)
        privacyPolicy?.setOnClickListener(this)
        registerComplaint = hView.findViewById(R.id.registerComplaint)
        registerComplaint?.setOnClickListener(this)
        contactUs = hView.findViewById(R.id.contactUs)
        contactUs?.setOnClickListener(this)
        followUs = hView.findViewById(R.id.followUs)
        followUs?.setOnClickListener(this)
        rateUs = hView.findViewById(R.id.rateUs)
        rateUs?.setOnClickListener(this)
        settings = hView.findViewById(R.id.settings)
        settings?.setOnClickListener(this)
        logout = hView.findViewById(R.id.logout)
        logout?.setOnClickListener(this)

        layoutcp = hView.findViewById(R.id.layoutcp)
        layoutcp?.setOnClickListener(this)

        layoutnotification=findViewById(R.id.layoutnotification);
        layoutnotification?.setOnClickListener(this)
        layoutsearch=findViewById(R.id.layoutsearch);
        layoutsearch?.setOnClickListener(this)
        binding!!.layoutdraweropen.setOnClickListener(this)
        binding!!.layouthelp.setOnClickListener(this)
        homeCustomerFragment = HomeCustomerFragment()
        customerFOStatusFragment = CustomerFOStatusFragment()

        binding!!.bottomNavigationView.selectedItemId = R.id.navigation_fohome
        loadFragment(homeCustomerFragment)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding!!.drawerfomainlayout,
            R.string.nav_open,
            R.string.nav_close
        )
        binding!!.drawerfomainlayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()
        binding!!.imgdrawer.setOnClickListener { view: View? ->
            if (binding!!.drawerfomainlayout.isDrawerOpen(GravityCompat.START)) {
                binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            } else {
                binding!!.drawerfomainlayout.openDrawer(GravityCompat.START)
            }
        }
        getCurrentLOcation(this@DashboardActivity)
    }

    fun hideDrawer(){
        binding!!.drawerfomainlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
    override fun onClick(view: View) {
        if (view.id == R.id.layoutcp){
            hideDrawer()
            if(sharedPrefsHelper.isFormCompleted){
                if(CommonUtils.appInitFirstTime){
                    CommonUtils.appInitFirstTime = false
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, customerFOStatusFragment!!).commit()
                }else{
                    val i = Intent(this, FoRegistrationDashBoardActivity::class.java)
                    startActivity(i)
                }
            }else{
                val i = Intent(this, FoRegistrationDashBoardActivity::class.java)
                startActivity(i)
            }

        }
        if (view.id == R.id.myAccount) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.myRefer) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, ReferActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.myOffer) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, MyOfferActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.myWallet) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, MyWalletActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.about) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.termsAndCondition) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, TermsConditionActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.privacyPolicy) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.registerComplaint) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, RegisterComplianActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.contactUs) {
        }
        if (view.id == R.id.followUs) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, FollowUsActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.rateUs) {
            binding!!.drawerfomainlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, ReatingActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.settings) {
        }
        if (view.id == R.id.layouthelp) {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.layoutnotification) {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        if (view.id == R.id.logout) {
            finish()
            sharedPrefsHelper.forceLogout(this@DashboardActivity)
        }
        if (view === binding!!.layoutdraweropen) {
            binding!!.drawerfomainlayout.openDrawer(Gravity.LEFT)
        }
        if (view.id==R.id.layoutsearch){
            val intent = Intent(this, AproveStatusActivity::class.java)
            startActivity(intent)
        }
        if(view.id == R.id.docApprovel){
            val intent = Intent(this, KycListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (count == 1) {
                finish()
            } else {
                CommonUtils.toast(this, "Press once again to exit!")
            }
            count++
            true
        } else super.onKeyDown(keyCode, event)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit()
            return true
        }
        return false
    }

    companion object {
        private const val TAG = "Dashboard"
    }

    fun getCurrentLOcation(dashboardActivity: DashboardActivity) {
        client= LocationServices.getFusedLocationProviderClient(this@DashboardActivity)
        Dexter.withContext(dashboardActivity)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    getmyLocation()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(p0: com.karumi.dexter.listener.PermissionRequest?,token: PermissionToken? ) {
                    token?.continuePermissionRequest()
                }

            }).check()
    }
    fun getmyLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        var tast= client.lastLocation
        tast.addOnSuccessListener(object: OnSuccessListener<Location> {
            override fun onSuccess(location: Location?) {

                /*smf.getMapAsync( object : OnMapReadyCallback {
                    override fun onMapReady(googleMap: GoogleMap) {
                        var latLng= location?.let { LatLng(it?.latitude,location?.longitude) }
                        var markerOptions= MarkerOptions().position(latLng).title("You are here ......!!")
                        googleMap.addMarker(markerOptions)
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
                    }

                })*/
                try {
                    val geo = Geocoder(
                        this@DashboardActivity.getApplicationContext(),
                        Locale.getDefault()
                    )
                    val addresses: List<Address> =
                        location?.let { geo.getFromLocation(it.latitude, it.longitude, 1) } as List<Address>
                    if (addresses.isEmpty()) {
                        /*yourtextfieldname.setText("Waiting for Location")*/
                    } else {
                        if (addresses.size > 0) {

                            binding?.txtlcoationname?.setText(
                                /*addresses[0].getFeatureName().toString() + ", " +*/ addresses[0].getLocality() /*+ ", " + addresses[0].getAdminArea() + ", " + addresses[0].getCountryName()*/
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace() // getFromLocation() may sometimes fail
                }
            }

        })
    }
}