package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgPersonalReferencesBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.google.gson.JsonObject

class FoPersonalReferenceFragment(private val stepPosition: Int, private val stepName: String) : Fragment() {
    private lateinit var binding: FoFrgPersonalReferencesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgPersonalReferencesBinding.inflate(inflater, container, false)
        FranchiseeRegistrationViewModel()._selectedStepName.value = stepName

        binding.stepSubmit.setOnClickListener {
            senPersonalReferenceDetail()
        }

        return binding.root
    }

    private fun senPersonalReferenceDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("fullname", binding.otherName.text.toString().trim())
        jsonObject.addProperty("relation", binding.spnrOtherRelationType.selectedItem.toString().trim())
        jsonObject.addProperty("sex", binding.spnrOtherSex.selectedItem.toString().trim())
        jsonObject.addProperty("age", binding.otherAge.text.toString().trim())
        jsonObject.addProperty("occupation", binding.otherOccupation.text.toString().trim())
        jsonObject.addProperty("location", binding.otherLocation.text.toString().trim())
        jsonObject.addProperty("contact_number", binding.otherContact.text.toString().trim())
        jsonObject.addProperty("address", binding.otherAddress.text.toString().trim())

        // Call remote Api service to save the Family Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(
            requireActivity(),
            params,
            jsonObject,
            stepPosition
        )
    }
}
