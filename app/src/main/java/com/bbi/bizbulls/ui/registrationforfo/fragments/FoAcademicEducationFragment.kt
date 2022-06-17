package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.databinding.FoFrgAcademicEducationDetailsBinding
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.google.gson.JsonObject

class FoAcademicEducationFragment(private val stepPosition: Int) : Fragment() {
    private lateinit var binding: FoFrgAcademicEducationDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgAcademicEducationDetailsBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            senEducationDetail()
        }

        return binding.root
    }
    private fun senEducationDetail() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("qualication", binding.qualification.text.toString().trim())
        jsonObject.addProperty("institutename", binding.instituteName.text.toString().trim())
        jsonObject.addProperty("board_uni", binding.boardOrUniversity.text.toString().trim())
        jsonObject.addProperty("yearofpasing", binding.yearOfPassing.text.toString().trim())
        jsonObject.addProperty("percentage", binding.percent.text.toString().trim())

        // Call remote Api service to save the Education Detail
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition)
    }

}