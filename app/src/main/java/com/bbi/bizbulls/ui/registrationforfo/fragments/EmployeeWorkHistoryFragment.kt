package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.EmployeeFrgLeavePolicyBinding
import com.bbi.bizbulls.databinding.EmployeeFrgProfessionalReferencesBinding
import com.bbi.bizbulls.databinding.EmployeeFrgWorkHistoryBinding
import com.bbi.bizbulls.model.PersonalReferenceViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeWorkHistoryFragment(private val stepPosition: Int, private var actionType: Int) : Fragment() {
    private lateinit var binding: EmployeeFrgWorkHistoryBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmployeeFrgWorkHistoryBinding.inflate(inflater, container, false)

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
        val call = RetrofitClient.getUrl().workHistoryGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<ResponseBody> {
            override
            fun onResponse(
                    call: Call<ResponseBody>,
                    responseObject: Response<ResponseBody>) {
               /* if (responseObject.code() == 200) {
                    if (responseObject.body()!!.data[0] != null) {
                        setUpDataInUI(responseObject.body()!!.data[0])
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }*/
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })
    }
    private fun setUpDataInUI(data: PersonalReferenceViewRes.Data) {
        uid = data.id.toString()

       /* binding.otherName.setText(data.fullname)
        binding.spnrOtherRelationType.setSelection(CommonUtils.getIndex(binding.spnrOtherRelationType,data.relation))

        binding.spnrOtherSex.setSelection(CommonUtils.getIndex(binding.spnrOtherSex,data.sex))
        binding.otherAge.setText(data.age)
        binding.otherOccupation.setText(data.occupation)
        binding.otherLocation.setText(data.location)
        binding.otherContact.setText(data.contactNumber)

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
        binding.otherName.isEnabled = isEditable
        binding.spnrOtherRelationType.isEnabled = isEditable

        binding.spnrOtherSex.isEnabled = isEditable
        binding.otherAge.isEnabled = isEditable
        binding.otherOccupation.isEnabled = isEditable
        binding.otherLocation.isEnabled = isEditable
        binding.otherContact.isEnabled = isEditable*/

    }

    private fun senPersonalReferenceDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("organization", binding.edtOrganization.text.toString().trim())
        jsonObject.addProperty("field", binding.edtFiled.text.toString().trim())
        jsonObject.addProperty("location", binding.edtLocation.text.toString().trim())
        jsonObject.addProperty("date_of_joining", binding.edtDateofJoining.text.toString().trim())
        jsonObject.addProperty("date_of_leaving", binding.edtDateofJoining.text.toString().trim())
        jsonObject.addProperty("product_service", binding.edtProductService.text.toString().trim())
        jsonObject.addProperty("job_designation", binding.edtJobDesignation.text.toString().trim())
        jsonObject.addProperty("department", binding.edtDepartment.text.toString().trim())
        jsonObject.addProperty("reporting_to", binding.edtReportTo.text.toString().trim())
        jsonObject.addProperty("business_targets", binding.edtBusinessTargets.text.toString().trim())
        jsonObject.addProperty("total_cost_to_company", binding.edtTotalCostToCompany.text.toString().trim())
        jsonObject.addProperty("take_home_per_month", binding.edtTakeHomePerMonth.text.toString().trim())
        jsonObject.addProperty("pf_number", binding.edtPFNumber.text.toString().trim())
        jsonObject.addProperty("health_card_number", binding.edtHealthCardNumber.text.toString().trim())
        jsonObject.addProperty("other_benefits", binding.edtOtherDetails.text.toString().trim())
        jsonObject.addProperty("achievements", binding.edtAchievements.text.toString().trim())
        jsonObject.addProperty("reasons_for_change", binding.edtReasonForChange.text.toString().trim())
        jsonObject.addProperty("job_description", binding.edtJobDescription.text.toString().trim())
        jsonObject.addProperty("contact_board_number", binding.edtContactBoardNumber.text.toString().trim())
        jsonObject.addProperty("official_mail_id", binding.edtOfficialMailId.text.toString().trim())
        jsonObject.addProperty("web_site", binding.edtWebsite.text.toString().trim())

        var jsonArray = JsonArray()
        jsonArray.add(jsonObject)

        var jsonObjectData = JsonObject()
        jsonObjectData.add("data",jsonArray)

        // Call remote Api service to save the Family Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(
            requireActivity(),
            params,
            jsonObjectData,
            stepPosition,actionType,uid
        )
    }
}
