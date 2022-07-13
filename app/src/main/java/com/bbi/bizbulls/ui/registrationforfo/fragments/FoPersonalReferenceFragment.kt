package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgPersonalReferencesBinding
import com.bbi.bizbulls.model.PersonalReferenceViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoPersonalReferenceFragment(private val stepPosition: Int,private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgPersonalReferencesBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgPersonalReferencesBinding.inflate(inflater, container, false)

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
        val call = RetrofitClient.getUrl().personalReferencesDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<PersonalReferenceViewRes> {
            override
            fun onResponse(
                    call: Call<PersonalReferenceViewRes>,
                    responseObject: Response<PersonalReferenceViewRes>) {
                if (responseObject.code() == 200) {
                    if (responseObject.body()!!.data?.get(0)   != null) {
                        responseObject.body()!!.data?.get(0)?.let { setUpDataInUI(it) }
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<PersonalReferenceViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })
    }
    private fun setUpDataInUI(data: PersonalReferenceViewRes.Data) {
        uid = data.id.toString()

        binding.otherName.setText(data.fullname)
        binding.spnrOtherRelationType.setSelection(CommonUtils.getIndex(binding.spnrOtherRelationType,
            data.relation.toString()
        ))

        binding.spnrOtherSex.setSelection(CommonUtils.getIndex(binding.spnrOtherSex,data.sex.toString()))
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
        binding.otherContact.isEnabled = isEditable

    }
    private fun senPersonalReferenceDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("fullname", binding.otherName.text.toString().trim())
        jsonObject.addProperty("relation", binding.spnrOtherRelationType.selectedItem.toString().trim())
        jsonObject.addProperty("sex", binding.spnrOtherSex.selectedItem.toString().trim())
        jsonObject.addProperty("age", binding.otherAge.text.toString().trim())
        jsonObject.addProperty("occupation", binding.otherOccupation.text.toString().trim())
        jsonObject.addProperty("location", binding.otherLocation.text.toString().trim())
        jsonObject.addProperty("contact_number", binding.otherContact.text.toString().trim())
        jsonObject.addProperty("address", binding.otherAddress.text.toString().trim())

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
