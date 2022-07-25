package com.bbi.bizbulls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bbi.bizbulls.adapter.AgreementApprovalAdapter
import com.bbi.bizbulls.databinding.ActivityKycDocViewBinding
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.DialogDocApprove
import com.bbi.bizbulls.utils.MyProcessDialog
import com.bbi.bizbulls.adapter.DocApprovalAdapter
import com.bbi.bizbulls.adapter.LicenseApprovalAdapter
import com.bbi.bizbulls.adapter.SetupApprovalAdapter
import com.bbi.bizbulls.model.*
import com.foldio.android.adapter.LocationApprovalAdapter
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KycDocViewActivity : AppCompatActivity(),DocViewListener {
    lateinit var binding:ActivityKycDocViewBinding
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@KycDocViewActivity) }

    var approval_type = 0
    private lateinit var userData  : PersonalUserAll.Data
    private var docList : List<ApprovalDocRes.Data> = arrayListOf()
    private var docListLocation : List<LocationApprovalRes.Data> = arrayListOf()
    private var docListAgreement : List<AgreementsApprovalRes.Data> = arrayListOf()
    private var docListStaff : List<StaffApprovalRes.Data> = arrayListOf()
    private var docListLicense : List<StaffApprovalRes.Data> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityKycDocViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        approval_type = intent.getIntExtra("APPROVAL_TYPE",0)
        userData = intent.getSerializableExtra("model") as PersonalUserAll.Data

        Log.d("UserData","id : "+ userData.userId)

        initView()
        if(approval_type == 0){
            getUserList()
        }else if(approval_type == 1){
            binding.linerApprove.visibility = View.GONE
            getLocationList()
        }else if(approval_type == 2){
            binding.linerApprove.visibility = View.GONE
            getAgreementList()
        }else if(approval_type == 3){
            binding.linerApprove.visibility = View.GONE
            getSetupList()
        }else if(approval_type == 4){
            binding.linerApprove.visibility = View.GONE
            getLicenseList()
        }

        binding.btnCancelFinal.setOnClickListener {
            onBackPressed()
        }
        binding.btnApproveFinal.setOnClickListener {
            callApi()
        }
    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.text="Approval Doc View"
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }

        binding.txtName.setText(userData.fullname)
        binding.txtAddress.setText(userData.permanentAdd.toString())
        binding.txtDate.setText(userData.emailid.toString())
        binding.txtFullname.setText(userData.emailid.toString())

        binding.txtCName.setText(userData.fullname.toString())
        binding.txtCGender.setText(userData.gender.toString())

        binding.txtCAddress.setText(userData.presentAdd.toString())
        binding.txtCAge.setText(userData.age.toString()+" Years")

    }
    private fun getUserList(){
        MyProcessDialog.showProgressBar(this, 0)
        val call: Call<ApprovalDocRes> =
            RetrofitClient.getUrl().viewUserDoc(sharedPrefsHelper.authToken,userData.userId.toString())
        call.enqueue(object : Callback<ApprovalDocRes> {
            override fun onResponse(
                call: Call<ApprovalDocRes>,
                response: Response<ApprovalDocRes>
            ) {
                if (response.isSuccessful) {
                    docList= response.body()?.data!!
                    setAdapter(docList)
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ApprovalDocRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }
    private fun getLocationList(){
        MyProcessDialog.showProgressBar(this, 0)
        val call: Call<LocationApprovalRes> =
            RetrofitClient.getUrl().getPendingLocation(sharedPrefsHelper.authToken,userData.userId.toString())
        call.enqueue(object : Callback<LocationApprovalRes> {
            override fun onResponse(
                call: Call<LocationApprovalRes>,
                response: Response<LocationApprovalRes>
            ) {
                if (response.isSuccessful) {
                    docListLocation = response.body()?.data!!
                    setAdapterLocation(docListLocation)
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<LocationApprovalRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }
    private fun getAgreementList(){
        MyProcessDialog.showProgressBar(this, 0)
        val call: Call<AgreementsApprovalRes> =
            RetrofitClient.getUrl().getPendingAgreement(sharedPrefsHelper.authToken,userData.userId.toString())
        call.enqueue(object : Callback<AgreementsApprovalRes> {
            override fun onResponse(
                call: Call<AgreementsApprovalRes>,
                response: Response<AgreementsApprovalRes>
            ) {
                if (response.isSuccessful) {
                    docListAgreement = response.body()?.data!!
                    setAdapterAgreement(docListAgreement)
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<AgreementsApprovalRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }
    fun getSetupList(){
        MyProcessDialog.showProgressBar(this, 0)
        val call: Call<StaffApprovalRes> =
            RetrofitClient.getUrl().getPendingStaff(sharedPrefsHelper.authToken,userData.userId.toString())
        call.enqueue(object : Callback<StaffApprovalRes> {
            override fun onResponse(
                call: Call<StaffApprovalRes>,
                response: Response<StaffApprovalRes>
            ) {
                if (response.isSuccessful) {
                    docListStaff = response.body()?.data!!
                    setAdapterSetUp(docListStaff)
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<StaffApprovalRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }
    fun getLicenseList(){
        MyProcessDialog.showProgressBar(this, 0)
        val call: Call<AgreementsApprovalRes> =
            RetrofitClient.getUrl().getPendingAgreement(sharedPrefsHelper.authToken,userData.userId.toString())
        call.enqueue(object : Callback<AgreementsApprovalRes> {
            override fun onResponse(
                call: Call<AgreementsApprovalRes>,
                response: Response<AgreementsApprovalRes>
            ) {
                if (response.isSuccessful) {
                    /*docListLicense = response.body()?.data!!
                    setAdapterLicense(docListLicense)*/
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<AgreementsApprovalRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }
    var setupApprovalAdapter : SetupApprovalAdapter? = null
    private fun setAdapterSetUp(userList: List<StaffApprovalRes.Data>?) {
        setupApprovalAdapter= SetupApprovalAdapter(this,userList,this,approval_type)
        binding?.listDocuments!!.layoutManager = GridLayoutManager(this,1)
        binding?.listDocuments?.adapter = setupApprovalAdapter

        var count = 0
        docListLocation.forEach {
            /*if(it.isApproved || it!!.documentStatus == "1" )
                count++*/
        }
        if(count == docListLocation.size){
            binding.linerApprove.visibility = View.GONE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 1f
            binding.btnCancelFinal.isEnabled = true
            binding.btnApproveFinal.isEnabled = true
        }else{
            binding.linerApprove.visibility = View.GONE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 0.5f
            binding.btnCancelFinal.isEnabled = false
            binding.btnApproveFinal.isEnabled = false
        }
    }
    var agreementApprovalAdapter : AgreementApprovalAdapter? = null
    private fun setAdapterAgreement(userList: List<AgreementsApprovalRes.Data>?) {
        agreementApprovalAdapter= AgreementApprovalAdapter(this,userList,this,approval_type)
        binding?.listDocuments!!.layoutManager = GridLayoutManager(this,1)
        binding?.listDocuments?.adapter = agreementApprovalAdapter

        var count = 0
        docListLocation.forEach {
            /*if(it.isApproved || it!!.documentStatus == "1" )
                count++*/
        }
        if(count == docListLocation.size){
            binding.linerApprove.visibility = View.GONE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 1f
            binding.btnCancelFinal.isEnabled = true
            binding.btnApproveFinal.isEnabled = true
        }else{
            binding.linerApprove.visibility = View.GONE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 0.5f
            binding.btnCancelFinal.isEnabled = false
            binding.btnApproveFinal.isEnabled = false
        }
    }
    var licenseApprovalAdapter : LicenseApprovalAdapter? = null
    private fun setAdapterLicense(userList: List<AgreementsApprovalRes.Data>?) {
        licenseApprovalAdapter= LicenseApprovalAdapter(this,userList,this,approval_type)
        binding?.listDocuments!!.layoutManager = GridLayoutManager(this,1)
        binding?.listDocuments?.adapter = licenseApprovalAdapter

        var count = 0
        docListLocation.forEach {
            /*if(it.isApproved || it!!.documentStatus == "1" )
                count++*/
        }
        if(count == docListLocation.size){
            binding.linerApprove.visibility = View.VISIBLE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 1f
            binding.btnCancelFinal.isEnabled = true
            binding.btnApproveFinal.isEnabled = true
        }else{
            binding.linerApprove.visibility = View.GONE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 0.5f
            binding.btnCancelFinal.isEnabled = false
            binding.btnApproveFinal.isEnabled = false
        }
    }
    var locationApprovalAdapter : LocationApprovalAdapter? = null
    private fun setAdapterLocation(userList: List<LocationApprovalRes.Data>?) {
        locationApprovalAdapter= LocationApprovalAdapter(this,userList,this,approval_type)
        binding?.listDocuments!!.layoutManager = GridLayoutManager(this,1)
        binding?.listDocuments?.adapter = locationApprovalAdapter

        var count = 0
        docListLocation.forEach {
            /*if(it.isApproved || it!!.documentStatus == "1" )
                count++*/
        }
        if(count == docListLocation.size){
            binding.linerApprove.visibility = View.GONE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 1f
            binding.btnCancelFinal.isEnabled = true
            binding.btnApproveFinal.isEnabled = true
        }else{
            binding.linerApprove.visibility = View.GONE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 0.5f
            binding.btnCancelFinal.isEnabled = false
            binding.btnApproveFinal.isEnabled = false
        }
    }

    var docApprovalAdapter : DocApprovalAdapter? = null
    private fun setAdapter(userList: List<ApprovalDocRes.Data>?) {
        docApprovalAdapter= DocApprovalAdapter(userList,this,approval_type)
        binding?.listDocuments!!.layoutManager = GridLayoutManager(this,2)
       binding?.listDocuments?.adapter = docApprovalAdapter

        var count = 0
        docList.forEach {
            if(it.isApproved || it!!.documentStatus == "1" )
                count++
        }
        if(count == docList.size){
            binding.linerApprove.visibility = View.VISIBLE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 1f
            binding.btnCancelFinal.isEnabled = true
            binding.btnApproveFinal.isEnabled = true
        }else{
            binding.linerApprove.visibility = View.VISIBLE
            binding.txtBg.visibility = View.VISIBLE
            binding.linerApprove.alpha = 0.5f
            binding.btnCancelFinal.isEnabled = false
            binding.btnApproveFinal.isEnabled = false
        }
    }

    private fun showDocDialog(data: ApprovalDocRes.Data?) {

        val docDialogDocApprove = DialogDocApprove(this)
        docDialogDocApprove.show()

        Picasso.get().load(
            data!!.documentName)
            .placeholder(R.drawable.img_default)
            .into(docDialogDocApprove.imdDocView)
        docDialogDocApprove.btnCancel!!.setOnClickListener {
            docDialogDocApprove.dismiss()
        }
        if(data.isApproved || data.documentStatus == "1"){
            docDialogDocApprove.btnApproval!!.setText("DisApprove")
        }else{
            docDialogDocApprove.btnApproval!!.setText("Approve")
        }
        docDialogDocApprove.btnApproval!!.setOnClickListener {
            docDialogDocApprove.dismiss()
            docList.forEach {
                if(it.documentId == data.documentId){
                    it.isApproved = !it.isApproved
                }
            }
            docApprovalAdapter?.notifyDataSetChanged()

            var count = 0;
            docList.forEach {
                if(it.isApproved)
                    count++
            }
            if(count == docList.size){
                binding.linerApprove.visibility = View.VISIBLE
                binding.txtBg.visibility = View.VISIBLE
                binding.linerApprove.alpha = 1f
                binding.btnCancelFinal.isEnabled = true
                binding.btnApproveFinal.isEnabled = true
            }else{
                binding.linerApprove.visibility = View.VISIBLE
                binding.txtBg.visibility = View.VISIBLE
                binding.linerApprove.alpha = 0.5f
                binding.btnCancelFinal.isEnabled = false
                binding.btnApproveFinal.isEnabled = false
            }
        }
    }
    private fun callApi(){
        MyProcessDialog.showProgressBar(this, 0)

        var jsonObject = JsonObject();
        jsonObject.addProperty("user_id",userData.userId)


        var jsonArray = JsonArray()

        var count = 0

        if(approval_type == 0){
            docList.forEach {
                var jsonObjectDoc = JsonObject()
                jsonObjectDoc.addProperty("doc_id",it!!.id.toString())
                if(it!!.documentType.toString() == "null"){
                    it!!.documentType = "1"
                }
                if(it!!.isApproved || it!!.documentStatus == "1" ) {
                    count++
                    jsonObjectDoc.addProperty("document_type","1")
                }else{
                    jsonObjectDoc.addProperty("document_type","0")
                }
                jsonArray.add(jsonObjectDoc)

            }

        }
        jsonObject.addProperty("status","approved")


        jsonObject.add("data",jsonArray)

        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().approveDoc(sharedPrefsHelper.authToken,jsonObject)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                   finish()
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }

    override fun onDocView(data: ApprovalDocRes.Data?) {
        showDocDialog(data)
    }

    override fun onDocLocationView(data: LocationApprovalRes.Data?) {
       // showDocLocationDialog(data)
        docListLocation.forEach {
            if(it.id == data!!.id){
                it.isApproved = !it.isApproved
            }
        }
        locationApprovalAdapter?.notifyDataSetChanged()

        MyProcessDialog.showProgressBar(this, 0)

        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().approvalLocation(sharedPrefsHelper.authToken,data!!.id.toString())
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                   // finish()
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }

    override fun onDocAgreementView(data: AgreementsApprovalRes.Data?) {
        docListAgreement.forEach {
            if(it.id == data!!.id){
                it.isApproved = !it.isApproved
            }
        }
        agreementApprovalAdapter?.notifyDataSetChanged()

        MyProcessDialog.showProgressBar(this, 0)

        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().approvalAgreement(sharedPrefsHelper.authToken,data!!.id.toString())
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    // finish()
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }

    override fun onDocSetupView(data: StaffApprovalRes.Data?) {
        docListStaff.forEach {
            if(it.id == data!!.id){
                it.isApproved = !it.isApproved
            }
        }
        setupApprovalAdapter?.notifyDataSetChanged()

        MyProcessDialog.showProgressBar(this, 0)

        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().approvalStaff(sharedPrefsHelper.authToken,data!!.id.toString())
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    // finish()
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }

}