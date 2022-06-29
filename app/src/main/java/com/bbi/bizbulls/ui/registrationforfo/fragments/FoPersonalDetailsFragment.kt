package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgPersonalDetailsBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser


class FoPersonalDetailsFragment(private val stepPosition: Int) : Fragment() {
    private lateinit var binding: FoFrgPersonalDetailsBinding
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

        return binding.root
    }

    private fun sendPersonalDetail() {
        val params: MutableMap<String, String> = HashMap()
        val jsonObject = JsonObject()
        jsonObject.addProperty("fullname" , binding.edtfullname.text.toString())
        jsonObject.addProperty("fathersname" , binding.edtfathername.text.toString())
        jsonObject.addProperty("mothersname", binding.edtmothersmaidensname.text.toString())
        jsonObject.addProperty("dob" , binding.edtdob.text.toString())
        jsonObject.addProperty("age" , binding.edtage.text.toString())
        jsonObject.addProperty("emailid" , binding.edtEmailid.text.toString())
        if (binding.radiomale.isChecked) {
            jsonObject.addProperty("gender", "Male")
        } else {
            jsonObject.addProperty("gender" , "Female")
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
        jsonObject.addProperty("permanent_add",binding.permAddress.text.toString())
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
        jsonObject.addProperty("permanent_acco_since",binding.permAccommodationSince.text.toString())
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
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition)
    }
}