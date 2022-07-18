package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgBankAccountDetailsBinding
import com.bbi.bizbulls.model.BranchDetailsViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoBankAccountFragment(private val stepPosition: Int,private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgBankAccountDetailsBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgBankAccountDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senBankDetail()
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
        val call = RetrofitClient.getUrl().bankDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<BranchDetailsViewRes> {
            override
            fun onResponse(
                    call: Call<BranchDetailsViewRes>,
                    responseObject: Response<BranchDetailsViewRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    if (responseObject.body()?.data?.get(0)  != null) {
                        var res = responseObject.body()!!.data?.last()
                        setUpDataInUI(res!!)
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<BranchDetailsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }
    private fun setUpDataInUI(data: BranchDetailsViewRes.Data) {
        uid = data?.id!!

        binding.bankName.setText(data.bankname)
        binding.edtbankAccountentName.setText(data.nameinaccount)
        binding.edtaccountnumber.setText(data.accountnumber)
        binding.edtbranch.setText(data.branch)
        binding.edtifsccode.setText(data.ifsccode)

        binding.spnrAccountType.setSelection(CommonUtils.getIndex(binding.spnrAccountType,data?.acctype!!))

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

        binding.bankName.isEnabled = isEditable
        binding.edtbankAccountentName.isEnabled = isEditable
        binding.edtaccountnumber.isEnabled = isEditable
        binding.edtbranch.isEnabled = isEditable
        binding.edtifsccode.isEnabled = isEditable
    }
    private fun senBankDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("bankname", binding.bankName.text.toString().trim())
        jsonObject.addProperty("nameinaccount", binding.edtbankAccountentName.text.toString().trim())
        jsonObject.addProperty("accountnumber", binding.edtaccountnumber.text.toString().trim())
        jsonObject.addProperty("branch", binding.edtbranch.text.toString().trim())
        jsonObject.addProperty("ifsccode", binding.edtifsccode.text.toString().trim())
        jsonObject.addProperty("acctype", binding.spnrAccountType.selectedItem.toString().trim())

        // Call remote Api service to save the Bank Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition,actionType,uid)
    }

}