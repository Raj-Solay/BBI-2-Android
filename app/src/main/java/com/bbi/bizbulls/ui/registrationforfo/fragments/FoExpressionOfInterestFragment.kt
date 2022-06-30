package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgExpressionOfInterestBinding
import com.bbi.bizbulls.model.PersonalDetailsViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoExpressionOfInterestFragment(private val stepPosition: Int,private var actionType : Int) : Fragment() {
    private lateinit var binding: FoFrgExpressionOfInterestBinding
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
        call?.enqueue(object : Callback<ResponseBody> {
            override
            fun onResponse(
                    call: Call<ResponseBody>,
                    responseObject: Response<ResponseBody>) {
               /* Log.d("ResponseViewData", "" + responseObject.body()!!.data[0].age)
                if (responseObject.code() == 200) {
                    if (responseObject.body()!!.data[0] != null) {
                       // setUpDataInUI(responseObject.body()!!.data[0])
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
    private fun setUpDataInUI(data: PersonalDetailsViewRes.Data) {

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
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition)    }

}