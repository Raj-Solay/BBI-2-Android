package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgHealthDetailsBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.google.gson.JsonObject

class FoHealthDetailsFragment(private val stepPosition: Int,private var actionType : Int) : Fragment() {
    private lateinit var binding: FoFrgHealthDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgHealthDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            sendHealthDetail()
        }

        if(actionType == CommonUtils.ACTION_TYPE_VIEW){

        }else if(actionType == CommonUtils.ACTION_TYPE_EDIT){
            //binding.edtheight.setText("0")
        }
        return binding.root
    }

    private fun sendHealthDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty(
            "birthidentificationmarks",
            binding.edtbirthmarkesone.text.toString().trim()
        )
        jsonObject.addProperty(
            "birthidentificationmarks2",
            binding.edtbirthmarketwo.text.toString().trim()
        )
        jsonObject.addProperty("handuse", binding.spnrhanduse.selectedItem.toString().trim())
        jsonObject.addProperty("height", binding.edtheight.text.toString().trim())
        jsonObject.addProperty("weight", binding.edtweight.text.toString().trim())
        jsonObject.addProperty("bloodgroup", binding.spnrbloodgroup.selectedItem.toString().trim())
        jsonObject.addProperty("willingtodonate", binding.edtwillingdonate.text.toString().trim())
        jsonObject.addProperty("physycalhandicape", binding.edttypeofph.text.toString().trim())
        jsonObject.addProperty("typeofph", binding.spnrhandicapped.selectedItem.toString().trim())
        jsonObject.addProperty(
            "surgelesstreatmentundergo",
            binding.spnrsurgeries.selectedItem.toString()
        )
        jsonObject.addProperty("typeofsurgery", binding.edttypeofsurgery.text.toString().trim())
        jsonObject.addProperty(
            "anyotherhealthissue",
            binding.spnrhealthissued.selectedItem.toString()
        )
        jsonObject.addProperty(
            "otherissuesdetail",
            binding.edttypeofhealthissue.text.toString().trim()
        )
        jsonObject.addProperty("anyunhealthyhabit", binding.spnrhabbit.selectedItem.toString())
        jsonObject.addProperty("habbitdetails", binding.edttypeofhabbits.text.toString().trim())

        // Call remote Api service to save the Health Details
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition)    }

}