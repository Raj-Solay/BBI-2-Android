package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgChildrenDetailsBinding
import com.bbi.bizbulls.model.ChildrenDetailsViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoChildDetailsFragment(private val stepPosition: Int,private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgChildrenDetailsBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgChildrenDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senChildrenDetail()
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
        val call = RetrofitClient.getUrl().childrenDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<ChildrenDetailsViewRes> {
            override
            fun onResponse(
                    call: Call<ChildrenDetailsViewRes>,
                    responseObject: Response<ChildrenDetailsViewRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    if (!responseObject.body()!!.data.isNullOrEmpty() && responseObject.body()?.data?.get(0)  != null) {
                        var res = responseObject.body()!!.data?.last()
                        setUpDataInUI(res!!)
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<ChildrenDetailsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }
    private fun setUpDataInUI(data: ChildrenDetailsViewRes.Data) {
        uid = data?.id.toString()

        binding.childName.setText(data.name)
        binding.childAge.setText(data.age)
        binding.spnrChildSex.setSelection(CommonUtils.getIndex(binding.spnrChildSex,data?.gender!!))
        binding.staysWith.setText(data.stayawith)

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
        binding.childName.isEnabled = isEditable
        binding.childAge.isEnabled = isEditable
        binding.spnrChildSex.isEnabled = isEditable
        binding.staysWith.isEnabled = isEditable
    }
    private fun senChildrenDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", binding.childName.text.toString().trim())
        jsonObject.addProperty("age", binding.childAge.text.toString().trim())
        jsonObject.addProperty("gender", binding.spnrChildSex.selectedItem.toString().trim())
        jsonObject.addProperty("stayawith", binding.staysWith.text.toString().trim())

        // Call remote Api service to save the Children Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition,actionType,uid)
    }
}