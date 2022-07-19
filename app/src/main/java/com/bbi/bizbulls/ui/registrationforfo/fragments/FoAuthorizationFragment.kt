package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.FoFrgAuthorizationBinding
import com.bbi.bizbulls.model.AuthorizationViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoAuthorizationFragment(private val stepPosition: Int,private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgAuthorizationBinding
    private var isCheckListChecked = false
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgAuthorizationBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            if (isCheckListChecked) {
                sendAuthorizationDetail()
            } else {
                CommonUtils.showError(
                    requireActivity(),
                    requireActivity().resources.getString(R.string.checkBoxSelection)
                )
            }
        }
        binding.checkAuthorization.setOnCheckedChangeListener { _, isChecked ->
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
        val call = RetrofitClient.getUrl().authorizationDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<AuthorizationViewRes> {
            override
            fun onResponse(
                    call: Call<AuthorizationViewRes>,
                    responseObject: Response<AuthorizationViewRes>) {
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
            fun onFailure(call: Call<AuthorizationViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }
    private fun setUpDataInUI(data: AuthorizationViewRes.Data) {
        uid = data?.id.toString()

        if(data.authorization == "Yes"){
            binding.checkAuthorization.isChecked = true
        }

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
        binding.checkAuthorization.isEnabled = isEditable
    }

    private fun sendAuthorizationDetail() {

        val jsonObject = JsonObject()
        jsonObject.addProperty("authorization", "Yes")
        jsonObject.addProperty("ip_address", CommonUtils.getDeviceId(requireActivity()))

        // Call remote Api service to save the Check List Details
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition,actionType,uid)
    }

}