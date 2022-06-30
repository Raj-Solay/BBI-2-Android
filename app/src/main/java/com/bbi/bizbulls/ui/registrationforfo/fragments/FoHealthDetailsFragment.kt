package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgHealthDetailsBinding
import com.bbi.bizbulls.model.HealthDetailsViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoHealthDetailsFragment(private val stepPosition: Int,private var actionType : Int) : Fragment() {
    private lateinit var binding: FoFrgHealthDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgHealthDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            sendHealthDetail()
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
        val call = RetrofitClient.getUrl().healthDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<HealthDetailsViewRes> {
            override
            fun onResponse(
                    call: Call<HealthDetailsViewRes>,
                    responseObject: Response<HealthDetailsViewRes>) {
                if (responseObject.code() == 200) {
                    if (responseObject.body()!!.data[0] != null) {
                        var list = responseObject.body()!!.data
                        var health = list.last()
                        setUpDataInUI(health)
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<HealthDetailsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }

    //private method of your class
    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

    private fun setUpDataInUI(health: HealthDetailsViewRes.Data) {

        binding.edtbirthmarkesone.setText(""+health.birthidentificationmarks)
        binding.edtbirthmarketwo.setText(""+health.birthidentificationmarks2)

        if(health.handuse.equals("Right hand")){
            binding.spnrhanduse.setSelection(0)
        }else{
            binding.spnrhanduse.setSelection(1)
        }

        binding.edtheight.setText("")
        binding.edtweight.setText(""+health.weight)

        binding.spnrbloodgroup.setSelection(getIndex(binding.spnrbloodgroup,health.bloodgroup))

        binding.edtwillingdonate.setText(""+ health.willingtodonate)
        binding.edttypeofph.setText(""+ health.typeofph)


        binding.spnrhandicapped.setSelection(0)

        binding.spnrsurgeries.setSelection(getIndex(binding.spnrsurgeries,health.surgelesstreatmentundergo))
        binding.edttypeofsurgery.setText(""+health.typeofsurgery)
        binding.spnrhealthissued.setSelection(getIndex(binding.spnrhealthissued,health.anyotherhealthissue))
        binding.edttypeofhealthissue.setText(""+health.otherissuesdetail)
        binding.spnrhabbit.setSelection(0)
        binding.edttypeofhabbits.setText(""+health.habbitdetails)

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

        binding.edtbirthmarkesone.isEnabled = isEditable
        binding.edtbirthmarketwo.isEnabled = isEditable

        binding.spnrhanduse.isEnabled = isEditable
        binding.spnrhanduse.isEnabled = isEditable

        binding.edtheight.isEnabled = isEditable
        binding.edtweight.isEnabled = isEditable

        binding.spnrbloodgroup.isEnabled = isEditable

        binding.edtwillingdonate.isEnabled = isEditable
        binding.edttypeofph.isEnabled = isEditable


        binding.spnrhandicapped.isEnabled = isEditable

        binding.spnrsurgeries.isEnabled = isEditable
        binding.edttypeofsurgery.isEnabled = isEditable
        binding.spnrhealthissued.isEnabled = isEditable
        binding.edttypeofhealthissue.isEnabled = isEditable
        binding.spnrhabbit.isEnabled = isEditable
        binding.edttypeofhabbits.isEnabled = isEditable
    }

    private fun sendHealthDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty(
            "birthidentificationmarks",
            binding.edtbirthmarkesone.text.toString().trim()
        )
        jsonObject.addProperty(
            "birthidentificationmarks2",
            binding.edtbirthmarketwo.text.toString().trim()
        )
        jsonObject.addProperty("handuse", binding.spnrhanduse.selectedItem.toString().trim())
        jsonObject.addProperty("height", binding.edtheight.text.toString().trim())
        jsonObject.addProperty("weight", binding.edtweight.text.toString().trim())
        jsonObject.addProperty("bloodgroup", binding.spnrbloodgroup.selectedItem.toString().trim())
        jsonObject.addProperty("willingtodonate", binding.edtwillingdonate.text.toString().trim())
        jsonObject.addProperty("physycalhandicape", binding.edttypeofph.text.toString().trim())
        jsonObject.addProperty("typeofph", binding.spnrhandicapped.selectedItem.toString().trim())
        jsonObject.addProperty(
            "surgelesstreatmentundergo",
            binding.spnrsurgeries.selectedItem.toString()
        )
        jsonObject.addProperty("typeofsurgery", binding.edttypeofsurgery.text.toString().trim())
        jsonObject.addProperty(
            "anyotherhealthissue",
            binding.spnrhealthissued.selectedItem.toString()
        )
        jsonObject.addProperty(
            "otherissuesdetail",
            binding.edttypeofhealthissue.text.toString().trim()
        )
        jsonObject.addProperty("anyunhealthyhabit", binding.spnrhabbit.selectedItem.toString())
        jsonObject.addProperty("habbitdetails", binding.edttypeofhabbits.text.toString().trim())

        // Call remote Api service to save the Health Details
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition,actionType)    }

}