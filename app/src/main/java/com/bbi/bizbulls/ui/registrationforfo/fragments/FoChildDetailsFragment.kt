package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgChildrenDetailsBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.google.gson.JsonObject

class FoChildDetailsFragment(private val stepPosition: Int, private val stepName: String) : Fragment() {
    private lateinit var binding: FoFrgChildrenDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgChildrenDetailsBinding.inflate(inflater, container, false)
        FranchiseeRegistrationViewModel()._selectedStepName.value = stepName

        binding.stepSubmit.setOnClickListener {
            senChildrenDetail()
        }

        return binding.root
    }

    private fun senChildrenDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", binding.childName.text.toString().trim())
        jsonObject.addProperty("age", binding.childAge.text.toString().trim())
        jsonObject.addProperty("gender", binding.spnrChildSex.selectedItem.toString().trim())
        jsonObject.addProperty("stayawith", binding.staysWith.text.toString().trim())

        // Call remote Api service to save the Children Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition)
    }
}