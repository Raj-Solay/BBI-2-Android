package com.bbi.bizbulls.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bbi.bizbulls.*
import com.bbi.bizbulls.databinding.FragmentStatusBinding
import com.bbi.bizbulls.media.DownloadFile
import com.bbi.bizbulls.model.DatesResponse
import com.bbi.bizbulls.model.FileUpload
import com.bbi.bizbulls.model.StatusData
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.adapter.DatesAdapter
import com.bbi.bizbulls.utils.Globals.IS_FRANCHISEE_FEE
import com.bbi.bizbulls.utils.Globals.REGISTRATION_FEES_DATA
import com.bbi.bizbulls.utils.Globals.REQUEST_CODE_ADD_AGREEMENT
import com.bbi.bizbulls.utils.Globals.REQUEST_CODE_ADD_FINABILITY
import com.bbi.bizbulls.utils.Globals.REQUEST_CODE_FRANCHISEE_FEE
import com.bbi.bizbulls.utils.Globals.REQUEST_CODE_LOCATION_SETUP
import com.bbi.bizbulls.utils.Globals.REQUEST_CODE_PDF_SELECT
import com.bbi.bizbulls.utils.Globals.REQUEST_CODE_REGISTRATION_FEE
import com.bbi.bizbulls.utils.Globals.REQUEST_CODE_SETUP
import com.bbi.bizbulls.utils.MyProcessDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class CustomerFOStatusFragment : Fragment(), View.OnClickListener {
    private lateinit var mDatesAdapter: DatesAdapter
    private val sharedPrefsHelper by lazy { SharedPrefsManager(requireActivity()) }
    private lateinit var binding: FragmentStatusBinding
    var isCustomer = false
    lateinit var statusData: StatusData
    var dates =  mutableListOf<String>()
    private lateinit var pdfUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusBinding.inflate(layoutInflater)
        binding.layoutkycincomplete.setOnClickListener(this)
        binding.btnaddlocationidentity.setOnClickListener(this)
        binding.btnaddsetup.setOnClickListener(this)
        binding.btnaddagreement.setOnClickListener(this)
        binding.btnaddlicense.setOnClickListener(this)
        binding.btnregister.setOnClickListener(this)
        binding.siteVisitLayout.btnReschedule.setOnClickListener(this)
        binding.finabilityLayout.btnAddFinability.setOnClickListener(this)
        binding.franchiseeFeeLayout.btnPayNow.setOnClickListener(this)
        binding.txtviewcontactdetails.setOnClickListener(this)
        binding.txtkycdownload.setOnClickListener(this)
        binding.txtregisterdownload.setOnClickListener(this)
        binding.bbiLayout.txtbbiAgreementdownload.setOnClickListener(this)
        binding.finabilityLayout.txtfinabilitydownload.setOnClickListener(this)
        binding.txtlocationidentitydownload.setOnClickListener(this)
        binding.txtagreementdownload.setOnClickListener(this)
        binding.siteVisitLayout.txtsiteVisitdownload.setOnClickListener(this)
        binding.franchiseeFeeLayout.txtfranchiseeFeedownload.setOnClickListener(this)
        binding.txtsetupdownload.setOnClickListener(this)


        binding.scrollfostatus.visibility = View.VISIBLE
        binding.layoutinfostatus.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geSteps(requireActivity())
    }

    override fun onClick(view: View) {
        if (view === binding.layoutkycincomplete) {
            setKycComplete()
        }
        if (view === binding.btnregister) {
            val intent = Intent(activity, OrderDetailActivity::class.java)
            intent.putExtra(REGISTRATION_FEES_DATA,statusData)
            intent.putExtra(IS_FRANCHISEE_FEE,false)
            startActivityForResult(intent,REQUEST_CODE_REGISTRATION_FEE)
        }
        if(view ==  binding.finabilityLayout.btnAddFinability){
            val intent = Intent(activity, FinabilityActivity::class.java)
            startActivityForResult(intent,REQUEST_CODE_ADD_FINABILITY)
        }
        if (view === binding.btnaddlocationidentity) {
            val intent = Intent(activity, OfficeLocationMapsActivity::class.java)
            intent.putExtra(REGISTRATION_FEES_DATA,statusData)
            startActivityForResult(intent,REQUEST_CODE_LOCATION_SETUP)
        }
        if (view === binding.btnaddagreement) {
            val intent = Intent(activity, AgreementsStatusActivity::class.java)
            intent.putExtra(REGISTRATION_FEES_DATA,statusData)
            startActivityForResult(intent,REQUEST_CODE_ADD_AGREEMENT)
        }
        if (view === binding.btnaddsetup) {
           setOfficeSetupDone()
            val intent = Intent(activity, SetupOfficeActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SETUP)
        }
        if (view === binding.btnaddlicense) {
            selectPdf()
        }
        if (view === binding.txtviewcontactdetails) {
            requireActivity().finish()
            sharedPrefsHelper.forceLogout(requireActivity())
        }
        if (view === binding.siteVisitLayout.btnReschedule) {
            getDates(requireActivity())
        }

        if (view === binding.franchiseeFeeLayout.btnPayNow){
            val intent = Intent(activity, OrderDetailActivity::class.java)
            intent.putExtra(REGISTRATION_FEES_DATA,statusData)
            intent.putExtra(IS_FRANCHISEE_FEE,true)
            startActivityForResult(intent, REQUEST_CODE_FRANCHISEE_FEE)
        }
        if (view === binding.txtkycdownload){
            DownloadFile.download(statusData.kyc.documentUrl,requireActivity())
        }
        if (view ===  binding.txtregisterdownload){
            DownloadFile.download(statusData.registrationFees.documentUrl,requireActivity())
        }
        if (view === binding.bbiLayout.txtbbiAgreementdownload){
            DownloadFile.download(statusData.bbAgreement.documentUrl,requireActivity())
        }
        if (view === binding.finabilityLayout.txtfinabilitydownload){
            DownloadFile.download(statusData.finability.documentUrl,requireActivity())
        }
        if (view === binding.txtlocationidentitydownload){
            DownloadFile.download(statusData.locationUpdate.documentUrl,requireActivity())
        }
        if (view === binding.txtagreementdownload){
            DownloadFile.download(statusData.agreement.documentUrl,requireActivity())
        }
        if (view === binding.siteVisitLayout.txtsiteVisitdownload){
            DownloadFile.download(statusData.siteVisit.documentUrl,requireActivity())
        }
        if (view ===  binding.franchiseeFeeLayout.txtfranchiseeFeedownload){
            DownloadFile.download(statusData.frenchiseeFee.documentUrl,requireActivity())
        }
        if (view === binding.txtsetupdownload){
            DownloadFile.download(statusData.setup.documentUrl,requireActivity())
        }
    }

    private fun setKycComplete() {
        binding.layoutkycincomplete.visibility = View.GONE
        binding.layoutkyccomplete.visibility = View.VISIBLE
        if(isCustomer)
        {
            binding.bbiLayout.layoutbbiAgreementincomplete.visibility = View.VISIBLE
        }
        else{
            binding.laoutregisterincomplete.visibility = View.VISIBLE
        }
        binding.imgkycstatus.setImageResource(R.drawable.ic_done)
    }

    private fun setBBIAgreementDone() {
        binding.bbiLayout.layoutbbiAgreementincomplete.visibility = View.GONE
        binding.bbiLayout.layoutbbiAgreementcomplete.visibility = View.VISIBLE
        binding.laoutregisterincomplete.visibility = View.VISIBLE
        binding.bbiLayout.imgbbiAgreementstatus.setImageResource(R.drawable.ic_done)
    }

    private fun showfinancialRelibility() {
        binding.laoutregisterincomplete.visibility = View.GONE
        binding.laoutregistercomplete.visibility = View.VISIBLE
        if(isCustomer) {
            binding.finabilityLayout.layoutfinabilityincomplete.visibility = View.VISIBLE
        } else {
            binding.layoutlocationidentityincomplete.visibility = View.VISIBLE
        }
        binding.imgregisterstatus.setImageResource(R.drawable.ic_done)
    }

    private fun setFinabilityDone() {
        binding.finabilityLayout.layoutfinabilityincomplete.visibility = View.GONE
        binding.finabilityLayout.layoutfinabilitycomplete.visibility = View.VISIBLE
        binding.layoutlocationidentityincomplete.visibility = View.VISIBLE
        binding.finabilityLayout.imgfinabilitystatus.setImageResource(R.drawable.ic_done)
    }
    private fun setLocationDone() {
        binding.layoutlocationidentityincomplete.visibility = View.GONE
        binding.layoutlocationidentitycomplete.visibility = View.VISIBLE
        binding.layoutagreementincomplete.visibility = View.VISIBLE
        binding.imglocationidentity.setImageResource(R.drawable.ic_done)
    }
    private fun setAgreementDone() {
        binding.layoutagreementincomplete.visibility = View.GONE
        binding.layoutagreementcomplete.visibility = View.VISIBLE
        if(isCustomer){
            binding.siteVisitLayout.layoutsiteVisitincomplete.visibility = View.VISIBLE
        }else{
            binding.layoutsetupincomplete.visibility = View.VISIBLE
        }
        binding.imgagreementstatus.setImageResource(R.drawable.ic_done)
    }

    private fun setSiteVisitDone() {
        binding.siteVisitLayout.layoutsiteVisitincomplete.visibility = View.GONE
        binding.siteVisitLayout.layoutsiteVisitcomplete.visibility = View.VISIBLE
        binding.franchiseeFeeLayout.layoutfranchiseeFeeincomplete.visibility = View.VISIBLE
        binding.siteVisitLayout.imgsiteVisitstatus.setImageResource(R.drawable.ic_done)
    }
    private fun setFranchiseeRegistrationFeeDone() {
        binding.franchiseeFeeLayout.layoutfranchiseeFeeincomplete.visibility = View.GONE
        binding.franchiseeFeeLayout.layoutfranchiseeFeecomplete.visibility = View.VISIBLE
        binding.layoutsetupincomplete.visibility = View.VISIBLE
        binding.franchiseeFeeLayout.imgfranchiseeFeestatus.setImageResource(R.drawable.ic_done)
    }
    private fun setOfficeSetupDone() {
        binding.layoutsetupincomplete.visibility = View.GONE
        binding.layoutsetupcomplete.visibility = View.VISIBLE
        binding.layoutlicenseincomplete.visibility = View.VISIBLE
        binding.imgsetupstatus.setImageResource(R.drawable.ic_done)
    }
    private fun setLicenceDone() {
        binding.layoutlicenseincomplete.visibility = View.GONE
        binding.layoutlicensecomplete.visibility = View.VISIBLE
        binding.imglicensestatus.setImageResource(R.drawable.ic_done)
        binding.txtinfomsg.visibility = View.VISIBLE
        binding.txtviewcontactdetails.visibility = View.VISIBLE
    }

    /**
     * Get Fo registration steps
     */
    fun geSteps(context: Context) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }

        val call: Call<StatusData> =
            RetrofitClient.getUrl().getStatus(sharedPrefsHelper.authToken)
        println("________URL ::${call.request().url}")
        println("________authToken ::${sharedPrefsHelper.authToken}")
        MyProcessDialog.showProgressBar(context, 0)
        call.enqueue(object : Callback<StatusData> {
            override
            fun onResponse(
                call: Call<StatusData>,
                responseObject: Response<StatusData>
            ) {
                if (responseObject.isSuccessful) {
                    statusData =  responseObject.body()!!
                    isCustomer = statusData.isCustomer
                    binding.apply {
                        txtcustomerid.text = statusData?.number
                        txtCustomerstatus.text = statusData?.customerStatus
                        txtcustomername.text = statusData?.customerName
                        txtcustomeraddress.text = statusData?.businessName
                        txtcustomertime.text = statusData?.date
                        dates = statusData.siteVisit.dates as MutableList<String>
                        setDatesData()
                    }
                    if(isCustomer){
                        binding.apply {
                            bbiLayout.bbiLayout.visibility= View.VISIBLE
                            finabilityLayout.finabilityLayout.visibility= View.VISIBLE
                            siteVisitLayout.finabilityLayout.visibility= View.VISIBLE
                            franchiseeFeeLayout.franchiseeFeeLayout.visibility= View.VISIBLE
                        }
                    }

                    if(statusData?.kyc?.status.equals("Completed",true))
                    {
                        setKycComplete()
                    }
                    if(statusData?.bbAgreement?.status.equals("Completed",true))
                    {
                        setBBIAgreementDone()
                    }
                    if(statusData?.registrationFees?.status.equals("Completed",true))
                    {
                        showfinancialRelibility()
                    }
                    if(statusData?.finability?.status.equals("Completed",true))
                    {
                        setFinabilityDone()
                    }
                    if(statusData?.locationUpdate?.status.equals("Completed",true))
                    {
                        setLocationDone()
                    }
                    if(statusData?.agreement?.status.equals("Completed",true))
                    {
                        setAgreementDone()
                    }
                    if(statusData?.frenchiseeFee?.status.equals("Completed",true))
                    {
                        setFranchiseeRegistrationFeeDone()
                    }
                    if(statusData?.siteVisit?.status.equals("Completed",true))
                    {
                        setSiteVisitDone()
                    }
                    if(statusData?.setup?.status.equals("Completed",true))
                    {
                        setOfficeSetupDone()
                    }
                    if(statusData?.licence?.status.equals("Completed",true))
                    {
                        setLicenceDone()
                    }

                } else {
                    RetrofitClient.showResponseMessage(context, responseObject.code())
                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<StatusData>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(context, t)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_REGISTRATION_FEE && resultCode == RESULT_OK) {
            showfinancialRelibility()
        }
        else if (requestCode == REQUEST_CODE_LOCATION_SETUP && resultCode == RESULT_OK) {
            setLocationDone()
        }
        else if (requestCode == REQUEST_CODE_ADD_AGREEMENT && resultCode == RESULT_OK) {
            setAgreementDone()
        }
        else if(requestCode == REQUEST_CODE_ADD_FINABILITY && resultCode == RESULT_OK){
            setFinabilityDone()
        }
        else if(requestCode == REQUEST_CODE_SETUP && resultCode == RESULT_OK){
            setOfficeSetupDone()
        }
        else if(requestCode == REQUEST_CODE_FRANCHISEE_FEE && resultCode == RESULT_OK){
            setFranchiseeRegistrationFeeDone()
        }
        else if(requestCode == REQUEST_CODE_FRANCHISEE_FEE && resultCode == RESULT_OK) {
            setFranchiseeRegistrationFeeDone()
        }
        else if (resultCode == RESULT_OK && resultCode ==REQUEST_CODE_PDF_SELECT) {

            pdfUri = data?.data!!
            val uri: Uri = data?.data!!
            val uriString: String = uri.toString()
            var pdfName: String? = null
            if (uriString.startsWith("content://")) {
                var myCursor: Cursor? = null
                try {
                    myCursor = requireActivity().contentResolver.query(uri, null, null, null, null)
                    if (myCursor != null && myCursor.moveToFirst()) {
                        pdfName = myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        uploadDocument(File(pdfName))
                    }
                } finally {
                    myCursor?.close()
                }
            }
        }
    }
    private fun setDatesData() {
        binding?.siteVisitLayout?.recycler?.layoutManager = GridLayoutManager(requireActivity(),3)
        mDatesAdapter = DatesAdapter(
            requireActivity(),dates
        )
        binding?.siteVisitLayout?.recycler?.adapter = mDatesAdapter
    }

    fun getDates(context: Context) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }

        val call: Call<DatesResponse> =
            RetrofitClient.getUrl().getDates(sharedPrefsHelper.authToken)
        MyProcessDialog.showProgressBar(context, 0)
        call.enqueue(object : Callback<DatesResponse> {
            override
            fun onResponse(
                call: Call<DatesResponse>,
                responseObject: Response<DatesResponse>
            ) {
                if (responseObject.isSuccessful) {
                    dates = responseObject.body()?.dates as MutableList<String>
                   setDatesData()

                } else {
                    RetrofitClient.showResponseMessage(context, responseObject.code())
                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<DatesResponse>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(context, t)
            }
        })
    }
    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, REQUEST_CODE_PDF_SELECT)
    }
    private fun uploadDocument(file: File) {
        val uri = FileProvider.getUriForFile(
            requireActivity(),
            requireActivity().packageName + ".fileProvider",
            file
        )
        val requestBody =
            RequestBody.create(requireActivity().contentResolver.getType(uri)?.toMediaTypeOrNull(), file)
        val part: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, requestBody)

        val sharedPrefsHelper by lazy { SharedPrefsManager(requireActivity()) }
        var call: Call<FileUpload>? = null
        call = RetrofitClient.getUrl()
            .uploadAsset(sharedPrefsHelper.authToken, part)
        if (MyProcessDialog.myAlertDialog != null && !MyProcessDialog.myAlertDialog?.isShowing!!)
            MyProcessDialog.showProgressBar(requireActivity(), 0)

        call.enqueue(object : Callback<FileUpload> {
            override
            fun onResponse(
                call: Call<FileUpload>,
                responseObject: Response<FileUpload>
            ) {
                if (responseObject.isSuccessful) {
                   setLicenceDone()
                } else {
                    RetrofitClient.showResponseMessage(
                        requireActivity(),
                        responseObject.code()
                    )

                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<FileUpload>, t: Throwable) {
                t.printStackTrace()
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireActivity(), t)
            }
        })
    }
}