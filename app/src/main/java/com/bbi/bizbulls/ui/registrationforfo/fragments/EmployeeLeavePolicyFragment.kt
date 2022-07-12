package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.EmployeeFrgLeavePolicyBinding
import com.bbi.bizbulls.databinding.EmployeeFrgProfessionalReferencesBinding
import com.bbi.bizbulls.model.LeavePolicy
import com.bbi.bizbulls.model.PersonalReferenceViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeLeavePolicyFragment(private val stepPosition: Int, private var actionType: Int) :
    Fragment() {
    private lateinit var binding: EmployeeFrgLeavePolicyBinding
    private var uid: String = ""
    private var leavePolicyList = arrayListOf<LeavePolicy>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmployeeFrgLeavePolicyBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senPersonalReferenceDetail()
        }

        binding.edtDate1.setOnClickListener {
            CommonUtils.commonDatePickerDialog(
                requireActivity(),
                object : CommonUtils.DatePickerListener {
                    override fun setDate(dateStr: String?) {
                        binding.edtDate1.setText(dateStr)
                    }
                })
        }

        binding.edtDate2.setOnClickListener {
            CommonUtils.commonDatePickerDialog(
                requireActivity(),
                object : CommonUtils.DatePickerListener {
                    override fun setDate(dateStr: String?) {
                        binding.edtDate2.setText(dateStr)
                    }
                })
        }

        binding.edtDate3.setOnClickListener {
            CommonUtils.commonDatePickerDialog(
                requireActivity(),
                object : CommonUtils.DatePickerListener {
                    override fun setDate(dateStr: String?) {
                        binding.edtDate3.setText(dateStr)
                    }
                })
        }

        binding.edtDate4.setOnClickListener {
            CommonUtils.commonDatePickerDialog(
                requireActivity(),
                object : CommonUtils.DatePickerListener {
                    override fun setDate(dateStr: String?) {
                        binding.edtDate4.setText(dateStr)
                    }
                })
        }

        binding.edtDate5.setOnClickListener {
            CommonUtils.commonDatePickerDialog(
                requireActivity(),
                object : CommonUtils.DatePickerListener {
                    override fun setDate(dateStr: String?) {
                        binding.edtDate5.setText(dateStr)
                    }
                })
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
        val call = RetrofitClient.getUrl().leaveHolidaysGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<ResponseBody> {
            override
            fun onResponse(
                call: Call<ResponseBody>,
                responseObject: Response<ResponseBody>
            ) {
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

    private fun setUpDataInUI(data: PersonalReferenceViewRes.Data) {
        uid = data.id.toString()



    }

    private fun senPersonalReferenceDetail() {
        val jsonObject1 = JsonObject()
        jsonObject1.addProperty("occasion", binding.edtFestival1.text.toString().trim())
        jsonObject1.addProperty("date_of_festival", binding.edtDate1.text.toString().trim())

        val jsonObject2 = JsonObject()
        jsonObject2.addProperty("occasion", binding.edtFestival2.text.toString().trim())
        jsonObject2.addProperty("date_of_festival", binding.edtDate2.text.toString().trim())

        val jsonObject3 = JsonObject()
        jsonObject3.addProperty("occasion", binding.edtFestival3.text.toString().trim())
        jsonObject3.addProperty("date_of_festival", binding.edtDate3.text.toString().trim())

        val jsonObject4 = JsonObject()
        jsonObject4.addProperty("occasion", binding.edtFestival4.text.toString().trim())
        jsonObject4.addProperty("date_of_festival", binding.edtDate4.text.toString().trim())

        val jsonObject5 = JsonObject()
        jsonObject5.addProperty("occasion", binding.edtFestival5.text.toString().trim())
        jsonObject5.addProperty("date_of_festival", binding.edtDate5.text.toString().trim())

        val jsonObjectRoot = JsonObject()
        jsonObjectRoot.addProperty("rotational_name", binding.otherAge.text.toString().trim())

        var jsonArray = JsonArray()
        jsonArray.add(jsonObject1)
        jsonArray.add(jsonObject2)
        jsonArray.add(jsonObject3)
        jsonArray.add(jsonObject4)
        jsonArray.add(jsonObject5)

        jsonObjectRoot.add("leave_holidays",jsonArray)


        // Call remote Api service to save the Family Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(
            requireActivity(),
            params,
            jsonObjectRoot,
            stepPosition, actionType, uid
        )
    }
}
