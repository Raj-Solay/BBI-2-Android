package com.biz.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.biz.bizbulls.databinding.EmployeeFrgReferralDetailsBinding
import com.biz.bizbulls.model.ReferralDetailsView
import com.biz.bizbulls.remote.RetrofitClient
import com.biz.bizbulls.sharedpref.SharedPrefsManager
import com.biz.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.biz.bizbulls.utils.CommonUtils
import com.biz.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeReferralDetailsFragment(private val stepPosition: Int, private var actionType: Int) : Fragment() {
    private lateinit var binding: EmployeeFrgReferralDetailsBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmployeeFrgReferralDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senPersonalReferenceDetail()
        }
        when (actionType) {
            CommonUtils.ACTION_TYPE_VIEW -> {
                getRecordFromAPI(false)
            }
            CommonUtils.ACTION_TYPE_EDIT -> {
                getRecordFromAPI(true)
                binding.stepSubmit.setText("Update")
            }
            CommonUtils.ACTION_TYPE_ADD -> {
                binding.stepSubmit.setText("Submit")
            }
        }
        return binding.root
    }

    private fun getRecordFromAPI(isFromEdit: Boolean) {
        MyProcessDialog.showProgressBar(requireContext(), 0)
        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        val call = RetrofitClient.getUrl().referralGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<ReferralDetailsView> {
            override
            fun onResponse(
                    call: Call<ReferralDetailsView>,
                    responseObject: Response<ReferralDetailsView>) {
                if (responseObject.code() == 200) {
                    if (!responseObject.body()!!.data.isNullOrEmpty() && responseObject.body()!!.data?.get(0)   != null) {
                        responseObject.body()!!.data?.get(0)?.let { setUpDataInUI(it) }
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<ReferralDetailsView>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })
    }
    private fun setUpDataInUI(data: ReferralDetailsView.Data) {
        //uid = data.id.toString()

        binding.edtDepartment.setText(data.department)
        binding.edtEmpCode.setText(data.epmCode)
        binding.edtDesignation.setText(data.designation)
        binding.edtCompany.setText(data.company)
        binding.edtReportingTo.setText(data.reportingTo)
        binding.edtPeriodOfWork.setText(data.periodOfWork)
        binding.edtLocation.setText(data.workLocation)
        binding.edtHowDoYouKnow.setText(data.howDoYouknow)
        binding.edtContactNo.setText(data.contactNo)
        binding.edtMailId.setText(data.mailId)

        var isEditable = false
        when (actionType) {
            CommonUtils.ACTION_TYPE_VIEW -> {
                isEditable = false
                binding.stepSubmit.visibility = View.INVISIBLE
            }
            CommonUtils.ACTION_TYPE_EDIT -> {
                isEditable = true
                binding.stepSubmit.visibility = View.VISIBLE
            }
            CommonUtils.ACTION_TYPE_ADD -> {
                isEditable = true
                binding.stepSubmit.visibility = View.VISIBLE
            }
        }
        binding.edtDepartment.isEnabled = isEditable
        binding.edtEmpCode.isEnabled = isEditable
        binding.edtDesignation.isEnabled = isEditable
        binding.edtCompany.isEnabled = isEditable
        binding.edtReportingTo.isEnabled = isEditable
        binding.edtPeriodOfWork.isEnabled = isEditable
        binding.edtLocation.isEnabled = isEditable
        binding.edtHowDoYouKnow.isEnabled = isEditable
        binding.edtContactNo.isEnabled = isEditable
        binding.edtMailId.isEnabled = isEditable

    }
    private fun senPersonalReferenceDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", binding.edtName.text.toString().trim())
        jsonObject.addProperty("epm_code", binding.edtEmpCode.text.toString().trim())
        jsonObject.addProperty("designation", binding.edtDesignation.text.toString().trim())
        jsonObject.addProperty("department", binding.edtDepartment.text.toString().trim())
        jsonObject.addProperty("company", binding.edtCompany.text.toString().trim())
        jsonObject.addProperty("period_of_work", binding.edtPeriodOfWork.text.toString().trim())
        jsonObject.addProperty("reporting_to", binding.edtReportingTo.text.toString().trim())
        jsonObject.addProperty("work_location", binding.edtLocation.text.toString().trim())
        jsonObject.addProperty("how_do_youknow", binding.edtHowDoYouKnow.text.toString().trim())
        jsonObject.addProperty("contact_no", binding.edtContactNo.text.toString().trim())
        jsonObject.addProperty("mail_id", binding.edtMailId.text.toString().trim())

        // Call remote Api service to save the Family Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(
            requireActivity(),
            params,
            jsonObject,
            stepPosition,actionType,uid
        )
    }
}
