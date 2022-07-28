package com.biz.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.biz.bizbulls.databinding.FoFrgAcademicEducationDetailsBinding
import com.biz.bizbulls.model.EducationDetailsViewRes
import com.biz.bizbulls.remote.RetrofitClient
import com.biz.bizbulls.sharedpref.SharedPrefsManager
import com.biz.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.biz.bizbulls.utils.CommonUtils
import com.biz.bizbulls.utils.Globals
import com.biz.bizbulls.utils.MyProcessDialog
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

        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }

        if(sharedPrefsHelper.role != null && !sharedPrefsHelper.role.isNullOrEmpty()
            && sharedPrefsHelper.role.toInt() == Globals.USER_TYPE_EMPLOYEE){
            binding.txtTCTitle.visibility = View.VISIBLE
            binding.txtTitle2.visibility = View.VISIBLE
            binding.txtTitle3.visibility = View.VISIBLE
            binding.tcCourse.visibility = View.VISIBLE
            binding.tcInstituteName.visibility = View.VISIBLE
            binding.tcYear.visibility = View.VISIBLE
            binding.tcLevel.visibility = View.VISIBLE
            binding.extraActivities.visibility = View.VISIBLE
            binding.achievements.visibility = View.VISIBLE
            binding.hobbies.visibility = View.VISIBLE
        }else{
            binding.title4.visibility = View.GONE
            binding.txtTCTitle.visibility = View.GONE
            binding.txtTitle2.visibility = View.GONE
            binding.txtTitle3.visibility = View.GONE
            binding.frm1.visibility = View.GONE
            binding.frm2.visibility = View.GONE
            binding.frm3.visibility = View.GONE
            binding.frm4.visibility = View.GONE
            binding.frm5.visibility = View.GONE
            binding.frm6.visibility = View.GONE
            binding.frm7.visibility = View.GONE
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
                    if (!responseObject.body()!!.data.isNullOrEmpty() &&  responseObject.body()!!.data?.get(0) != null) {
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

        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        if(sharedPrefsHelper.role.toInt() == Globals.USER_TYPE_EMPLOYEE){

            binding.tcCourse.setText(data.tc_course)
            binding.tcInstituteName.setText(data.tc_institution)
            binding.tcYear.setText(data.tc_year)
            binding.tcLevel.setText(data.tc_level)
            binding.extraActivities.setText(data.extra_co_activities)
            binding.achievements.setText(data.curricular_achivements)
            binding.hobbies.setText(data.tc_hobbies)
        }else{
            binding.tcCourse.setText("")
            binding.tcInstituteName.setText("")
            binding.tcYear.setText("")
            binding.tcLevel.setText("")
            binding.extraActivities.setText("")
            binding.achievements.setText("")
            binding.hobbies.setText("")
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

        binding.qualification.isEnabled = isEditable
        binding.instituteName.isEnabled = isEditable
        binding.boardOrUniversity.isEnabled = isEditable
        binding.yearOfPassing.isEnabled = isEditable
        binding.percent.isEnabled = isEditable

        if(sharedPrefsHelper.role.toInt() == Globals.USER_TYPE_EMPLOYEE){
            binding.tcCourse.isEnabled = isEditable
            binding.tcInstituteName.isEnabled = isEditable
            binding.tcYear.isEnabled = isEditable
            binding.tcLevel.isEnabled = isEditable
            binding.extraActivities.isEnabled = isEditable
            binding.achievements.isEnabled = isEditable
            binding.hobbies.isEnabled = isEditable
        }
    }

    private fun senEducationDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("qualication", binding.qualification.text.toString().trim())
        jsonObject.addProperty("institutename", binding.instituteName.text.toString().trim())
        jsonObject.addProperty("board_uni", binding.boardOrUniversity.text.toString().trim())
        jsonObject.addProperty("yearofpasing", binding.yearOfPassing.text.toString().trim())
        jsonObject.addProperty("percentage", binding.percent.text.toString().trim())

        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        if(sharedPrefsHelper.role.toInt() == Globals.USER_TYPE_EMPLOYEE){
            jsonObject.addProperty("tc_course", binding.tcCourse.text.toString().trim())
            jsonObject.addProperty("tc_institution", binding.tcInstituteName.text.toString().trim())
            jsonObject.addProperty("tc_year", binding.tcYear.text.toString().trim())
            jsonObject.addProperty("tc_level", binding.tcLevel.text.toString().trim())
            jsonObject.addProperty("extra_co_activities", binding.extraActivities.text.toString().trim())
            jsonObject.addProperty("curricular_achivements", binding.achievements.text.toString().trim())
            jsonObject.addProperty("hobbies", binding.hobbies.text.toString().trim())
        }
        // Call remote Api service to save the Education Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition,actionType,uid)
    }

}