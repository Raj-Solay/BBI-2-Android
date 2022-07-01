package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgAcademicEducationDetailsBinding
import com.bbi.bizbulls.model.EducationDetailsViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoAcademicEducationFragment(private val stepPosition: Int,private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgAcademicEducationDetailsBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgAcademicEducationDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senEducationDetail()
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
        val call = RetrofitClient.getUrl().educationDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<EducationDetailsViewRes> {
            override
            fun onResponse(
                    call: Call<EducationDetailsViewRes>,
                    responseObject: Response<EducationDetailsViewRes>) {
               if (responseObject.code() == 200 || responseObject.code() == 201) {
                    if (responseObject.body()!!.data?.get(0) != null) {
                        var res = responseObject.body()!!.data?.last()
                        setUpDataInUI(res!!)
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<EducationDetailsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }
    private fun setUpDataInUI(data: EducationDetailsViewRes.Data) {
        uid = data?.id.toString()
        binding.qualification.setText(data.qualication)
        binding.instituteName.setText(data.institutename)
        binding.boardOrUniversity.setText(data.boardUni)
        binding.yearOfPassing.setText(data.yearofpasing)
        binding.percent.setText(data.percentage)

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

        binding.qualification.isEnabled = isEditable
        binding.instituteName.isEnabled = isEditable
        binding.boardOrUniversity.isEnabled = isEditable
        binding.yearOfPassing.isEnabled = isEditable
        binding.percent.isEnabled = isEditable
    }

    private fun senEducationDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("qualication", binding.qualification.text.toString().trim())
        jsonObject.addProperty("institutename", binding.instituteName.text.toString().trim())
        jsonObject.addProperty("board_uni", binding.boardOrUniversity.text.toString().trim())
        jsonObject.addProperty("yearofpasing", binding.yearOfPassing.text.toString().trim())
        jsonObject.addProperty("percentage", binding.percent.text.toString().trim())

        // Call remote Api service to save the Education Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition,actionType,uid)
    }

}