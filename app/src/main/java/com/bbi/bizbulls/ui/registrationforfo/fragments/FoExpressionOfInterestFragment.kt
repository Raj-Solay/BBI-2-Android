package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgExpressionOfInterestBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.google.gson.JsonObject

class FoExpressionOfInterestFragment : Fragment() {
    private lateinit var binding: FoFrgExpressionOfInterestBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgExpressionOfInterestBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            sendExpressionDetail()
        }

        return binding.root
    }

    private fun sendExpressionDetail() {
        val jsonObject = JsonObject()

        jsonObject.addProperty("project_name", binding.interestedProjectName.text.toString().trim())
        jsonObject.addProperty("industry", binding.industryOfProject.text.toString().trim())
        jsonObject.addProperty("location_interest", binding.locationOfInterest.text.toString().trim())
        jsonObject.addProperty("financial_assistance", binding.spnrFinancialAssistance.selectedItem.toString().trim())
        jsonObject.addProperty("registration_fee", binding.registrationFee.text.toString().trim())
        jsonObject.addProperty("franchisee_planning_for", binding.franchisePlanningFor.text.toString().trim())
       // jsonObject.addProperty("franchisee_planning_for_other", binding.franchisePlanningAs.text.toString().trim())
        jsonObject.addProperty("franchisee_planning_as", binding.franchisePlanningAs.text.toString().trim())
        jsonObject.addProperty("business_place_type", binding.spnrBusinessPlaceType.selectedItem.toString().trim())
        jsonObject.addProperty("business_place_size", binding.businessPlaceSize.text.toString().trim())

        // Call remote Api service to save the Health Details
        FranchiseeRegistrationViewModel().sendExpressionOfInterestDetail(requireActivity(), jsonObject)
    }

}