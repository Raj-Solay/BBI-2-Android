package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.FoFrgChecklistUnderstandingBinding
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

class FoCheckListFragment(private val stepPosition: Int,private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgChecklistUnderstandingBinding
    private var isCheckListChecked = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgChecklistUnderstandingBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            if(isCheckListChecked) {
                senCheckListDetail()
            }else{
                CommonUtils.showError(requireActivity(), requireActivity().resources.getString(R.string.checkBoxSelection))
            }
        }
        binding.checklistForFranchise.setOnCheckedChangeListener { _, isChecked ->
            isCheckListChecked = isChecked
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
        val call = RetrofitClient.getUrl().checkListDetailsGET(sharedPrefsHelper.authToken)
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

    private fun senCheckListDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("meet_eligibility_criteria", "Yes")
        jsonObject.addProperty("spend_required_amount", "Yes")
        jsonObject.addProperty("business_registration_support", "Yes")
        jsonObject.addProperty("business_loan_support", "Yes")
        jsonObject.addProperty("accept_undergo_business_training", "Yes")
        jsonObject.addProperty("accept_company_referred_setup", "Yes")
        jsonObject.addProperty("understood_terms_conditions", "Yes")
        jsonObject.addProperty("authorized_keep_contact", "Yes")
        jsonObject.addProperty("no_relation_clients", "Yes")
        jsonObject.addProperty("your_responsibility", "Yes")

        // Call remote Api service to save the Check List Details
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition)
    }

}