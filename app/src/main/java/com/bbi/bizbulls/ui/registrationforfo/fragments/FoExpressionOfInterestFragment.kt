package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgExpressionOfInterestBinding
import com.bbi.bizbulls.model.ExpressionDetailsViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoExpressionOfInterestFragment(private val stepPosition: Int,private var actionType : Int) : Fragment() {
    private lateinit var binding: FoFrgExpressionOfInterestBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgExpressionOfInterestBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            sendExpressionDetail()
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
        val call = RetrofitClient.getUrl().expressionInterestDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<ExpressionDetailsViewRes> {
            override
            fun onResponse(
                    call: Call<ExpressionDetailsViewRes>,
                    responseObject: Response<ExpressionDetailsViewRes>) {
                if (responseObject.code() == 200) {
                    if (responseObject.body()?.data?.get(0)  != null) {
                        setUpDataInUI(responseObject.body()?.data!![0])
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<ExpressionDetailsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }
    private fun setUpDataInUI(data: ExpressionDetailsViewRes.Data) {
        uid = data.id.toString()

        binding.interestedProjectName.setText(data.projectName)
        binding.industryOfProject.setText(data.industry)
        binding.locationOfInterest.setText(data.locationInterest)

        binding.spnrFinancialAssistance.setSelection(CommonUtils.getIndex(binding.spnrFinancialAssistance,data.financialAssistance!!))

        binding.registrationFee.setText(data.registrationFee)
        binding.franchisePlanningFor.setText(data.franchiseePlanningFor)
        binding.franchisePlanningAs.setText(data.franchiseePlanningAs)

        binding.spnrBusinessPlaceType.setSelection(CommonUtils.getIndex(binding.spnrBusinessPlaceType,data.businessPlaceType!!))
        binding.businessPlaceSize.setText(data.businessPlaceSize)

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

        binding.interestedProjectName.isEnabled = isEditable
        binding.industryOfProject.isEnabled = isEditable
        binding.locationOfInterest.isEnabled = isEditable

        binding.spnrFinancialAssistance.isEnabled = isEditable

        binding.registrationFee.isEnabled = isEditable
        binding.franchisePlanningFor.isEnabled = isEditable
        binding.franchisePlanningAs.isEnabled = isEditable

        binding.spnrBusinessPlaceType.isEnabled = isEditable
        binding.businessPlaceSize.isEnabled = isEditable
    }

    private fun sendExpressionDetail() {
        val jsonObject = JsonObject()

        jsonObject.addProperty("project_name", binding.interestedProjectName.text.toString().trim())
        jsonObject.addProperty("industry", binding.industryOfProject.text.toString().trim())
        jsonObject.addProperty("location_interest", binding.locationOfInterest.text.toString().trim())
        jsonObject.addProperty("financial_assistance", binding.spnrFinancialAssistance.selectedItem.toString().trim())
        jsonObject.addProperty("registration_fee", binding.registrationFee.text.toString().trim())
        jsonObject.addProperty("franchisee_planning_for", binding.franchisePlanningFor.text.toString().trim())
       // jsonObject.addProperty("franchisee_planning_for_other", binding.franchisePlanningAs.text.toString().trim())
        jsonObject.addProperty("franchisee_planning_as", binding.franchisePlanningAs.text.toString().trim())
        jsonObject.addProperty("business_place_type", binding.spnrBusinessPlaceType.selectedItem.toString().trim())
        jsonObject.addProperty("business_place_size", binding.businessPlaceSize.text.toString().trim())

        // Call remote Api service to save the Health Details
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition, actionType, uid)    }

}