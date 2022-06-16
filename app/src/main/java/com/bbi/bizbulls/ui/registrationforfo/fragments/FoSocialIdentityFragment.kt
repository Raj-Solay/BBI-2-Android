package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgSocialIdentityDetailsBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.google.gson.JsonObject

class FoSocialIdentityFragment : Fragment() {
    private lateinit var binding: FoFrgSocialIdentityDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgSocialIdentityDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senSocialIdentityDetail()
        }

        return binding.root
    }

    private fun senSocialIdentityDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("aadhaar_number", binding.aadharCardNumber.text.toString().trim())
        jsonObject.addProperty("driving_licence_number", binding.drivingLicence.text.toString().trim())
        jsonObject.addProperty("pancard_number", binding.pancard.text.toString().trim())
        jsonObject.addProperty("passport_number", binding.passport.text.toString().trim())
        jsonObject.addProperty("voter_card_number", binding.voterId.text.toString().trim())
        jsonObject.addProperty("ration_card_number", binding.rationCard.text.toString().trim())

        // Call remote Api service to save the Social Identity Detail
        FranchiseeRegistrationViewModel().sendSocialIdentityDetail(requireActivity(), jsonObject)
    }
}