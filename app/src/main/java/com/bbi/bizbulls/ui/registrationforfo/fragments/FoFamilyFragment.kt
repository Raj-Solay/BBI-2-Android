package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgFamilyDetailsBinding
import com.bbi.bizbulls.model.FamilyDetailsViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoFamilyFragment(private val stepPosition: Int,private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgFamilyDetailsBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgFamilyDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senFamilyDetail()
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
        val call = RetrofitClient.getUrl().familyDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<FamilyDetailsViewRes> {
            override
            fun onResponse(
                    call: Call<FamilyDetailsViewRes>,
                    responseObject: Response<FamilyDetailsViewRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    if (!responseObject.body()!!.data.isNullOrEmpty() && responseObject.body()!!.data?.get(0)   != null) {
                        responseObject.body()!!.data?.get(0)?.let { setUpDataInUI(it) }
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<FamilyDetailsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }
    private fun setUpDataInUI(data: FamilyDetailsViewRes.Data) {
        uid = data.id.toString()

        binding.familyName.setText(data.name)
        binding.spnrRelationType.setSelection(CommonUtils.getIndex(binding.spnrRelationType,data.relation!!))

        binding.age.setText(data.age)
        binding.education.setText(data.education)
        binding.occupation.setText(data.occupation)
        binding.monthlyIncome.setText(data.monthlyincome)
        binding.contactNoFamily.setText(data.contactno)
        binding.permanentAddressFamily.setText(data.address)

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

        binding.familyName.isEnabled = isEditable
        binding.spnrRelationType.isEnabled = isEditable

        binding.age.isEnabled = isEditable
        binding.education.isEnabled = isEditable
        binding.occupation.isEnabled = isEditable
        binding.monthlyIncome.isEnabled = isEditable
        binding.contactNoFamily.isEnabled = isEditable
        binding.permanentAddressFamily.isEnabled = isEditable
    }
    private fun senFamilyDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", binding.familyName.text.toString().trim())
        jsonObject.addProperty("relation", binding.spnrRelationType.selectedItem.toString().trim())
        jsonObject.addProperty("age", binding.age.text.toString().trim())
        jsonObject.addProperty("education", binding.education.text.toString().trim())
        jsonObject.addProperty("occupation", binding.occupation.text.toString().trim())
        jsonObject.addProperty("monthlyincome", binding.monthlyIncome.text.toString().trim())
        jsonObject.addProperty("contactno", binding.contactNoFamily.text.toString().trim())
        jsonObject.addProperty("address", binding.permanentAddressFamily.text.toString().trim())


        // Call remote Api service to save the Family Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition,actionType,uid)
    }

}