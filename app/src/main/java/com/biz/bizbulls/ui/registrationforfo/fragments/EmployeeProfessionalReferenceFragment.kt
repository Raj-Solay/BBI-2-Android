package com.biz.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.biz.bizbulls.databinding.EmployeeFrgProfessionalReferencesBinding
import com.biz.bizbulls.model.ProfessionalReferenceViewRes
import com.biz.bizbulls.remote.RetrofitClient
import com.biz.bizbulls.sharedpref.SharedPrefsManager
import com.biz.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.biz.bizbulls.utils.CommonUtils
import com.biz.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeProfessionalReferenceFragment(private val stepPosition: Int, private var actionType: Int) : Fragment() {
    private lateinit var binding: EmployeeFrgProfessionalReferencesBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmployeeFrgProfessionalReferencesBinding.inflate(inflater, container, false)

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
        val call = RetrofitClient.getUrl().professionalReferencesGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<ProfessionalReferenceViewRes> {
            override
            fun onResponse(
                    call: Call<ProfessionalReferenceViewRes>,
                    responseObject: Response<ProfessionalReferenceViewRes>) {
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
            fun onFailure(call: Call<ProfessionalReferenceViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })
    }
    private fun setUpDataInUI(data: ProfessionalReferenceViewRes.Data) {
     //   uid = data.id.toString()

        binding.edtName.setText(data.name)
        binding.edtDesignation.setText(data.designation)
        binding.edtDepartment.setText(data.department)
        binding.edtOrgWorking.setText(data.organizationWorking)
        binding.edtPeriodContact.setText(data.periodContact)
        binding.edtContactType.setText(data.contactType)
        binding.edtOfficeNumber.setText(data.officeNumber)
        binding.edtPersonalContact.setText(data.personalContact)
        binding.edtAddress.setText(data.address)
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

        binding.edtName.isEnabled = isEditable
        binding.edtDesignation.isEnabled = isEditable
        binding.edtDepartment.isEnabled = isEditable
        binding.edtOrgWorking.isEnabled = isEditable
        binding.edtPeriodContact.isEnabled = isEditable
        binding.edtContactType.isEnabled = isEditable
        binding.edtOfficeNumber.isEnabled = isEditable
        binding.edtPersonalContact.isEnabled = isEditable
        binding.edtAddress.isEnabled = isEditable
        binding.edtMailId.isEnabled = isEditable


    }
    private fun senPersonalReferenceDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", binding.edtName.text.toString().trim())
        jsonObject.addProperty("designation", binding.edtDesignation.text.toString().trim())
        jsonObject.addProperty("department", binding.edtDepartment.text.toString().trim())
        jsonObject.addProperty("organization_working", binding.edtOrgWorking.text.toString().trim())
        jsonObject.addProperty("period_contact", binding.edtPeriodContact.text.toString().trim())
        jsonObject.addProperty("contact_type", binding.edtContactType.text.toString().trim())
        jsonObject.addProperty("office_number", binding.edtOfficeNumber.text.toString().trim())
        jsonObject.addProperty("personal_contact", binding.edtPersonalContact.text.toString().trim())
        jsonObject.addProperty("address", binding.edtAddress.text.toString().trim())
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
