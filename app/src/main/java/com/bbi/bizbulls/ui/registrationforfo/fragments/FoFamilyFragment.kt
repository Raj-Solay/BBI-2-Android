package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgFamilyDetailsBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.google.gson.JsonObject

class FoFamilyFragment(private val stepPosition: Int, private val stepName: String) : Fragment() {
    private lateinit var binding: FoFrgFamilyDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgFamilyDetailsBinding.inflate(inflater, container, false)
        FranchiseeRegistrationViewModel()._selectedStepName.value = stepName

        binding.stepSubmit.setOnClickListener {
            senFamilyDetail()
        }

        return binding.root
    }
    private fun senFamilyDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", binding.familyName.text.toString().trim())
        jsonObject.addProperty("relation", binding.spnrRelationType.selectedItem.toString().trim())
        jsonObject.addProperty("age", binding.age.text.toString().trim())
        jsonObject.addProperty("education", binding.education.text.toString().trim())
        jsonObject.addProperty("occupation", binding.occupation.text.toString().trim())
        jsonObject.addProperty("monthlyincome", binding.monthlyIncome.text.toString().trim())
        jsonObject.addProperty("contactno", binding.contactNoFamily.text.toString().trim())
        jsonObject.addProperty("address", binding.permanentAddressFamily.text.toString().trim())


        // Call remote Api service to save the Family Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition)    }

}