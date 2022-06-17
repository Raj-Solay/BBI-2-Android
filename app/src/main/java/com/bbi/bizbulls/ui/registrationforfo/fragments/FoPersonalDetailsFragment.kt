package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgPersonalDetailsBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.google.gson.JsonObject

class FoPersonalDetailsFragment(private val stepPosition: Int, private val stepName: String) : Fragment() {
    private lateinit var binding: FoFrgPersonalDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FoFrgPersonalDetailsBinding.inflate(inflater, container, false)
        FranchiseeRegistrationViewModel()._selectedStepName.value = stepName
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
        params["fathersname"] = binding.edtfathername.text.toString()
        params["mothersname"] = binding.edtmothersmaidensname.text.toString()
        params["dob"] = binding.edtdob.text.toString()
        if (binding.radiomale.isChecked) {
            params["gender"] = "Male"
        } else {
            params["gender"] = "Female"
        }
        params["pob"] = binding.edtplaceofbirth.text.toString()
        params["mothertongue"] = binding.edtmothertongue.text.toString()
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
        params["maritalstatus"] = maritalstatus
        params["alternatenumber"] = binding.edtaltnumber.text.toString()
        params["whatsappnumber"] = binding.edtwhatsappnumber.text.toString()

        //permanent  address
        params["permanent_add"] = binding.permAddress.text.toString()
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
        params["permanent_acco_type"] = permanentAccoType
        params["permanent_acco_since"] = binding.permAccommodationSince.text.toString()
        params["permanent_emergency_no"] = binding.permEmergencyContact.text.toString()


        //present address
        params["present_add"] = binding.presAddress.text.toString()
        params["state"] = binding.edtState.text.toString()
        params["zip"] = binding.edtZip.text.toString()
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
        params["present_acco_type"] = presentAccoType
        params["present_acco_since"] = binding.presAccommodationSince.text.toString()
        params["present_emergency_no"] = binding.permEmergencyContact.text.toString()

        // Call remote Api service to save the Personal Details
        val jsonObject = JsonObject()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition)
    }
}