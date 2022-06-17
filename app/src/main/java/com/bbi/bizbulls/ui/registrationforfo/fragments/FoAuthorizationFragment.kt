package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.FoFrgAuthorizationBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.google.gson.JsonObject


class FoAuthorizationFragment(private val stepPosition: Int) : Fragment() {
    private lateinit var binding: FoFrgAuthorizationBinding
    private var isCheckListChecked = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgAuthorizationBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            if (isCheckListChecked) {
                sendAuthorizationDetail()
            } else {
                CommonUtils.showError(
                    requireActivity(),
                    requireActivity().resources.getString(R.string.checkBoxSelection)
                )
            }
        }
        binding.checkAuthorization.setOnCheckedChangeListener { _, isChecked ->
            isCheckListChecked = isChecked
        }
        return binding.root
    }

    private fun sendAuthorizationDetail() {

        val jsonObject = JsonObject()
        jsonObject.addProperty("authorization", "Yes")
        jsonObject.addProperty("ip_address", CommonUtils.getDeviceId(requireActivity()))

        // Call remote Api service to save the Check List Details
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition)
    }

}