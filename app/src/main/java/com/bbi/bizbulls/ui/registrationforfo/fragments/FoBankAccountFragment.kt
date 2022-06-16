package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgBankAccountDetailsBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.google.gson.JsonObject

class FoBankAccountFragment : Fragment() {
    private lateinit var binding: FoFrgBankAccountDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgBankAccountDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senBankDetail()
        }

        return binding.root
    }

    private fun senBankDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("bankname", binding.bankName.text.toString().trim())
        jsonObject.addProperty("nameinaccount", binding.edtbankAccountentName.text.toString().trim())
        jsonObject.addProperty("accountnumber", binding.edtaccountnumber.text.toString().trim())
        jsonObject.addProperty("branch", binding.edtbranch.text.toString().trim())
        jsonObject.addProperty("ifsccode", binding.edtifsccode.text.toString().trim())
        jsonObject.addProperty("acctype", binding.spnrAccountType.selectedItem.toString().trim())

        // Call remote Api service to save the Bank Detail
        FranchiseeRegistrationViewModel().sendBankDetail(requireActivity(), jsonObject)
    }

}