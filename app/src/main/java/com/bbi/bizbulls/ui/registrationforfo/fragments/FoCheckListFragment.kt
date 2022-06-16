package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.FoFrgChecklistUnderstandingBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.google.gson.JsonObject

class FoCheckListFragment : Fragment() {
    private lateinit var binding: FoFrgChecklistUnderstandingBinding
    private var isCheckListChecked = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgChecklistUnderstandingBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            if(isCheckListChecked) {
                senCheckListDetail()
            }else{
                CommonUtils.showError(requireActivity(), requireActivity().resources.getString(R.string.checkBoxSelection))
            }
        }
        binding.checklistForFranchise.setOnCheckedChangeListener { _, isChecked ->
            isCheckListChecked = isChecked
        }
        return binding.root
    }

    private fun senCheckListDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("meet_eligibility_criteria", "Yes")
        jsonObject.addProperty("spend_required_amount", "Yes")
        jsonObject.addProperty("business_registration_support", "Yes")
        jsonObject.addProperty("business_loan_support", "Yes")
        jsonObject.addProperty("accept_undergo_business_training", "Yes")
        jsonObject.addProperty("accept_company_referred_setup", "Yes")
        jsonObject.addProperty("understood_terms_conditions", "Yes")
        jsonObject.addProperty("authorized_keep_contact", "Yes")
        jsonObject.addProperty("no_relation_clients", "Yes")
        jsonObject.addProperty("your_responsibility", "Yes")

        // Call remote Api service to save the Health Details
        FranchiseeRegistrationViewModel().sendCheckListDetail(requireActivity(), jsonObject)
    }

}