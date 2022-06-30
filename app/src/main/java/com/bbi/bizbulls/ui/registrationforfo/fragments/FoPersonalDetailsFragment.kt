package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgPersonalDetailsBinding
import com.bbi.bizbulls.model.PersonalDetailsViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoPersonalDetailsFragment(private val stepPosition: Int, private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgPersonalDetailsBinding
    private var uid : String = ""
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FoFrgPersonalDetailsBinding.inflate(inflater, container, false)

        // personal details
        binding.edtdob.setOnClickListener {
            CommonUtils.commonDatePickerDialog(
                    requireActivity(),
                    object : CommonUtils.DatePickerListener {
                        override fun setDate(dateStr: String?) {
                            binding.edtdob.text = dateStr
                        }
                    })
        }
        binding.stepSubmit.setOnClickListener {
            sendPersonalDetail()
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
        val call = RetrofitClient.getUrl().personalDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<PersonalDetailsViewRes> {
            override
            fun onResponse(
                    call: Call<PersonalDetailsViewRes>,
                    responseObject: Response<PersonalDetailsViewRes>) {
              if (responseObject.code() == 200) {
                    if (responseObject.body()!!.data?.get(0)  != null) {
                        responseObject.body()!!.data?.get(0)?.let { setUpDataInUI(it) }
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<PersonalDetailsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }

    private fun setUpDataInUI(data: PersonalDetailsViewRes.Data) {
        uid = data.id.toString()
        binding.edtfullname.setText("" + data.fullname)
        binding.edtfathername.setText("" + data.fathersname)
        binding.edtmothersmaidensname.setText("" + data.mothersname)
        binding.edtdob.setText("" + data.dob)
        binding.edtplaceofbirth.setText("" + data.pob)
        binding.edtage.setText("" + data.age)
        binding.edtEmailid.setText("" + data.emailid)
        if (data.gender.equals("Male")) {
            binding.radiomale.isChecked = true
            binding.radiofemale.isChecked = false
        } else {
            binding.radiomale.isChecked = false
            binding.radiofemale.isChecked = true
        }
        binding.edtmothertongue.setText("" + data.mothertongue)

        if (data.maritalstatus.equals("Single")) {
            binding.radiosingle.isChecked = true
        } else if (data.maritalstatus.equals("Married")) {
            binding.radiomarried.isChecked = true
        } else if (data.maritalstatus.equals("Separated")) {
            binding.radioseparated.isChecked = true
        } else if (data.maritalstatus.equals("Widowed")) {
            binding.radiowidowed.isChecked = true
        }
        binding.edtwhatsappnumber.setText("" + data.whatsappnumber)
        binding.edtaltnumber.setText("" + data.alternatenumber)
        binding.permAddress.setText("" + data.permanentAdd)

        if (data.permanentAccoType.equals("Own")) {
            binding.radioPermOwn.isChecked = true
        } else if (data.permanentAccoType.equals("Rent")) {
            binding.radioPermRent.isChecked = true
        } else if (data.permanentAccoType.equals("Hotel")) {
            binding.radioPermHostel.isChecked = true
        } else if (data.permanentAccoType.equals("bachelor")) {
            binding.radioPermBachelor.isChecked = true
        }

        binding.permAccommodationSince.setText("" + data.permanentAccoSince)
        binding.presEmergencyContact.setText("" + data.permanentEmergencyNo)

        binding.presAddress.setText("" + data.presentAdd)
        binding.edtState.setText("" + data.state)
        binding.edtZip.setText("" + data.zip)

        if (data.presentAccoType.equals("Own")) {
            binding.radioPresOwn.isChecked = true
        } else if (data.presentAccoType.equals("Rent")) {
            binding.radioPresRent.isChecked = true
        } else if (data.presentAccoType.equals("Hotel")) {
            binding.radioPresHostel.isChecked = true
        } else if (data.presentAccoType.equals("bachelor")) {
            binding.radioPresHostel.isChecked = true
        }

        binding.presAccommodationSince.setText("" + data.presentAccoSince)
        binding.presEmergencyContact.setText("" + data.presentEmergencyNo)

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

        //if(!isEditable){
        binding.edtfullname.isEnabled = isEditable
        binding.edtfathername.isEnabled = isEditable
        binding.edtmothersmaidensname.isEnabled = isEditable
        binding.edtdob.isEnabled = isEditable
        binding.edtplaceofbirth.isEnabled = isEditable
        binding.edtage.isEnabled = isEditable
        binding.edtEmailid.isEnabled = isEditable
        binding.radiomale.isEnabled = isEditable
        binding.radiomale.isEnabled = isEditable

        binding.edtmothertongue.isEnabled = isEditable
        binding.radioseparated.isEnabled = isEditable
        binding.radiowidowed.isEnabled = isEditable
        binding.radiomarried.isEnabled = isEditable
        binding.radiosingle.isEnabled = isEditable

        binding.edtwhatsappnumber.isEnabled = isEditable
        binding.edtaltnumber.isEnabled = isEditable
        binding.permAddress.isEnabled = isEditable

        binding.radioPermRent.isEnabled = isEditable
        binding.radioPermOwn.isEnabled = isEditable
        binding.radioPermHostel.isEnabled = isEditable
        binding.radioPermBachelor.isEnabled = isEditable

        binding.permAccommodationSince.isEnabled = isEditable
        binding.presEmergencyContact.isEnabled = isEditable

        binding.presAddress.isEnabled = isEditable
        binding.edtState.isEnabled = isEditable
        binding.edtZip.isEnabled = isEditable

        binding.radioPresOwn.isEnabled = isEditable
        binding.radioPresRent.isEnabled = isEditable
        binding.radioPresHostel.isEnabled = isEditable
        binding.radioPresHostel.isEnabled = isEditable

        binding.presAccommodationSince.isEnabled = isEditable
        binding.presEmergencyContact.isEnabled = isEditable

        //  }

    }

    private fun sendPersonalDetail() {
        val params: MutableMap<String, String> = HashMap()
        val jsonObject = JsonObject()
        jsonObject.addProperty("fullname", binding.edtfullname.text.toString())
        jsonObject.addProperty("fathersname", binding.edtfathername.text.toString())
        jsonObject.addProperty("mothersname", binding.edtmothersmaidensname.text.toString())
        jsonObject.addProperty("dob", binding.edtdob.text.toString())
        jsonObject.addProperty("age", binding.edtage.text.toString())
        jsonObject.addProperty("emailid", binding.edtEmailid.text.toString())
        if (binding.radiomale.isChecked) {
            jsonObject.addProperty("gender", "Male")
        } else {
            jsonObject.addProperty("gender", "Female")
        }
        jsonObject.addProperty("pob", binding.edtplaceofbirth.text.toString())
        jsonObject.addProperty("mothertongue", binding.edtmothertongue.text.toString())
        var maritalstatus = ""
        if (binding.radiosingle.isChecked) {
            maritalstatus = "Single"
        } else if (binding.radiomarried.isChecked) {
            maritalstatus = "Married"
        } else if (binding.radioseparated.isChecked) {
            maritalstatus = "Separated"
        } else if (binding.radiowidowed.isChecked) {
            maritalstatus = "Widowed"
        }
        jsonObject.addProperty("maritalstatus", maritalstatus)
        jsonObject.addProperty("alternatenumber", binding.edtaltnumber.text.toString())
        jsonObject.addProperty("whatsappnumber", binding.edtwhatsappnumber.text.toString())

        //permanent  address
        jsonObject.addProperty("permanent_add", binding.permAddress.text.toString())
        var permanentAccoType = ""
        if (binding.radioPermOwn.isChecked) {
            permanentAccoType = "Own"
        } else if (binding.radioPermRent.isChecked) {
            permanentAccoType = "Rent"
        } else if (binding.radioPermHostel.isChecked) {
            permanentAccoType = "Hostel"
        } else if (binding.radioPermBachelor.isChecked) {
            permanentAccoType = "Bachelor"
        }
        jsonObject.addProperty("permanent_acco_type", permanentAccoType)
        jsonObject.addProperty("permanent_acco_since", binding.permAccommodationSince.text.toString())
        jsonObject.addProperty("permanent_emergency_no", binding.permEmergencyContact.text.toString())


        //present address
        jsonObject.addProperty("present_add", binding.presAddress.text.toString())
        jsonObject.addProperty("state", binding.edtState.text.toString())
        jsonObject.addProperty("zip", binding.edtZip.text.toString())
        var presentAccoType = ""
        if (binding.radioPresOwn.isChecked) {
            presentAccoType = "Own"
        } else if (binding.radioPresRent.isChecked) {
            presentAccoType = "Rent"
        } else if (binding.radioPresHostel.isChecked) {
            presentAccoType = "Hostel"
        } else if (binding.radioPresBachelor.isChecked) {
            presentAccoType = "Bachelor"
        }
        jsonObject.addProperty("present_acco_type", presentAccoType)
        jsonObject.addProperty("present_acco_since", binding.presAccommodationSince.text.toString())
        jsonObject.addProperty("present_emergency_no", binding.permEmergencyContact.text.toString())

        // Call remote Api service to save the Personal Details
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition, actionType,uid)
    }
}