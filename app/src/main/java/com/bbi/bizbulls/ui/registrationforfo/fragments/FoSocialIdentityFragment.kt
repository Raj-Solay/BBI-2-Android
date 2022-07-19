package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgSocialIdentityDetailsBinding
import com.bbi.bizbulls.model.PersonalDetailsViewRes
import com.bbi.bizbulls.model.SocialIdentifyViewRes
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

class FoSocialIdentityFragment(private val stepPosition: Int,private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgSocialIdentityDetailsBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgSocialIdentityDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senSocialIdentityDetail()
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
        val call = RetrofitClient.getUrl().socialIdentityDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<SocialIdentifyViewRes> {
            override
            fun onResponse(
                    call: Call<SocialIdentifyViewRes>,
                    responseObject: Response<SocialIdentifyViewRes>) {
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
            fun onFailure(call: Call<SocialIdentifyViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }
    private fun setUpDataInUI(data: SocialIdentifyViewRes.Data) {
        uid = data.id.toString()

        binding.aadharCardNumber.setText(data.aadhaarNumber)
        binding.pancard.setText(data.pancardNumber)
        binding.voterId.setText(data.voterCardNumber)
        binding.drivingLicence.setText(data.passportNumber)
        binding.rationCard.setText(data.rationCardNumber)
        binding.pancard.setText(data.passportNumber)

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

        binding.aadharCardNumber.isEnabled = isEditable
        binding.pancard.isEnabled = isEditable
        binding.voterId.isEnabled = isEditable
        binding.drivingLicence.isEnabled = isEditable
        binding.rationCard.isEnabled = isEditable


    }
    private fun senSocialIdentityDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("aadhaar_number", binding.aadharCardNumber.text.toString().trim())
        jsonObject.addProperty("driving_licence_number", binding.drivingLicence.text.toString().trim())
        jsonObject.addProperty("pancard_number", binding.pancard.text.toString().trim())
        jsonObject.addProperty("passport_number", binding.passport.text.toString().trim())
        jsonObject.addProperty("voter_card_number", binding.voterId.text.toString().trim())
        jsonObject.addProperty("ration_card_number", binding.rationCard.text.toString().trim())

        // Call remote Api service to save the Social Identity Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition,actionType,uid)    }
}