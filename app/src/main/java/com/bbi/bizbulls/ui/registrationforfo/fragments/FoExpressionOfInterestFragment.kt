package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.FoFrgExpressionOfInterestBinding
import com.bbi.bizbulls.model.CityRes
import com.bbi.bizbulls.model.ExpressionDetailsViewRes
import com.bbi.bizbulls.model.LocalityRes
import com.bbi.bizbulls.model.StateRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoExpressionOfInterestFragment(private val stepPosition: Int,private var actionType : Int) : Fragment() {
    private lateinit var binding: FoFrgExpressionOfInterestBinding
    private var uid : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoFrgExpressionOfInterestBinding.inflate(inflater, container, false)

        binding.stepSubmit.setOnClickListener {
            sendExpressionDetail()
        }

        when (actionType) {
            CommonUtils.ACTION_TYPE_VIEW -> {
                getRecordFromAPI(false)
            }
            CommonUtils.ACTION_TYPE_EDIT -> {
                getRecordFromAPI(true)
                getStateAPI()
                binding.stepSubmit.setText("Update")
            }
            CommonUtils.ACTION_TYPE_ADD -> {
                getStateAPI()
                binding.stepSubmit.setText("Submit")
            }
        }

        return binding.root
    }
    var stateList = arrayListOf<String>()
    var stateListId = arrayListOf<String>()
    var cityList = arrayListOf<String>()
    var cityListId = arrayListOf<String>()
    var localityList = arrayListOf<String>()
    var localityListId = arrayListOf<String>()

    val adapterState : ArrayAdapter<String>? = null
    val adapterCity : ArrayAdapter<String>? = null
    val adapterLocality : ArrayAdapter<String>? = null

    private fun getStateAPI(){

        stateList.add("Select State")
        cityList.add("Select City")
        localityList.add("Select Locality")

        stateListId.add("0")
        cityListId.add("0")
        localityListId.add("0")

        val adapterState = ArrayAdapter<String>(requireContext(),
            R.layout.spinner_item, stateList)
        binding.spnrState.adapter = adapterState

        val adapterCity = ArrayAdapter<String>(requireContext(),
            R.layout.spinner_item, cityList)
        binding.spnrCity.adapter = adapterCity

        val adapterLocality = ArrayAdapter<String>(requireContext(),
            R.layout.spinner_item, localityList)
        binding.spnrLocality.adapter = adapterLocality

        MyProcessDialog.showProgressBar(requireContext(), 0)
        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        val call = RetrofitClient.getUrl().getState(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<StateRes> {
            override
            fun onResponse(
                call: Call<StateRes>,
                responseObject: Response<StateRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    try{
                        var stateRes = responseObject.body()!!.data!!.state
                        stateRes!!.forEach {
                            stateList.add(it.name.toString())
                            stateListId.add(it.id.toString())
                        }
                       setStateAdapter()
                      //  getCityAPI()
                    }catch (e  :Exception){
                        e.printStackTrace()
                    }

                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<StateRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })
    }

    fun setStateAdapter(){
        val adapterState = ArrayAdapter<String>(requireContext(),
            R.layout.spinner_item, stateList)
        binding.spnrState.adapter = adapterState
        adapterState.notifyDataSetChanged()

        binding.spnrState.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position != 0){
                    getCityAPI(stateListId.get(position))
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })

        binding.spnrCity.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position != 0){
                    getLocalityApi(cityListId.get(position).toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })


        /*binding.spnrLocality.setOnItemClickListener { adapterView, view, i, l ->
            if(i != 0){
                getCityAPI()
            }
        }*/
    }

    private fun getCityAPI(stateId : String){
        var jsonObject = JsonObject();
        jsonObject.addProperty("state_id",stateId)
     //   MyProcessDialog.showProgressBar(requireContext(), 0)
        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        val call = RetrofitClient.getUrl().getCity(sharedPrefsHelper.authToken,jsonObject)
        call?.enqueue(object : Callback<CityRes> {
            override
            fun onResponse(
                call: Call<CityRes>,
                responseObject: Response<CityRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    var stateRes = responseObject.body()!!.data!!.city
                    if(stateRes!!.size> 0){
                        stateRes!!.forEach {
                            cityList.add(it.name.toString())
                            cityListId.add(it.id.toString())
                        }
                        val adapterState = ArrayAdapter<String>(requireContext(),
                            R.layout.spinner_item, cityList)
                        binding.spnrCity.adapter = adapterState
                        adapterState.notifyDataSetChanged()
                    }else{
                        cityList.add("City not found")
                        cityListId.add("0")
                        val adapterState = ArrayAdapter<String>(requireContext(),
                            R.layout.spinner_item, cityList)
                        binding.spnrCity.adapter = adapterState
                        adapterState.notifyDataSetChanged()
                    }

                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<CityRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })
    }
    private fun getLocalityApi(city_id :String){
        var jsonObject = JsonObject();
        jsonObject.addProperty("city_id",city_id)
       // MyProcessDialog.showProgressBar(requireContext(), 0)
        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        val call = RetrofitClient.getUrl().getLocality(sharedPrefsHelper.authToken,jsonObject)
        call?.enqueue(object : Callback<LocalityRes> {
            override
            fun onResponse(
                call: Call<LocalityRes>,
                responseObject: Response<LocalityRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    var stateRes = responseObject.body()!!.data!!.locality
                    if(stateRes!!.size > 0){
                        stateRes!!.forEach {
                            localityList.add(it.name.toString())
                            localityListId.add(it.id.toString())
                        }
                        val adapterState = ArrayAdapter<String>(requireContext(),
                            R.layout.spinner_item, localityList)
                        binding.spnrLocality.adapter = adapterState
                        adapterState.notifyDataSetChanged()
                    }else{
                        localityList.add("Locality not found")
                        localityListId.add("0")
                        val adapterState = ArrayAdapter<String>(requireContext(),
                            R.layout.spinner_item, localityList)
                        binding.spnrLocality.adapter = adapterState
                        adapterState.notifyDataSetChanged()
                    }

                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<LocalityRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })
    }

    private fun getRecordFromAPI(isFromEdit: Boolean) {
        MyProcessDialog.showProgressBar(requireContext(), 0)
        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        val call = RetrofitClient.getUrl().expressionInterestDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<ExpressionDetailsViewRes> {
            override
            fun onResponse(
                    call: Call<ExpressionDetailsViewRes>,
                    responseObject: Response<ExpressionDetailsViewRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    if (!responseObject.body()!!.data.isNullOrEmpty() &&  responseObject.body()?.data?.get(0)  != null) {
                        setUpDataInUI(responseObject.body()?.data!![0])
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<ExpressionDetailsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }
    private fun setUpDataInUI(data: ExpressionDetailsViewRes.Data) {
        uid = data.id.toString()

        binding.interestedProjectName.setText(data.projectName)
        binding.industryOfProject.setText(data.industry)
        binding.locationOfInterest.setText(data.locationInterest)

        binding.spnrFinancialAssistance.setSelection(CommonUtils.getIndex(binding.spnrFinancialAssistance,data.financialAssistance!!))

        binding.registrationFee.setText(data.registrationFee)
        binding.franchisePlanningFor.setText(data.franchiseePlanningFor)
        binding.franchisePlanningAs.setText(data.franchiseePlanningAs)

        binding.spnrBusinessPlaceType.setSelection(CommonUtils.getIndex(binding.spnrBusinessPlaceType,data.businessPlaceType!!))
        binding.businessPlaceSize.setText(data.businessPlaceSize)

        var isEditable = false
        when (actionType) {
            CommonUtils.ACTION_TYPE_VIEW -> {
                isEditable = false
                binding.stepSubmit.visibility = View.INVISIBLE
            }
            CommonUtils.ACTION_TYPE_EDIT -> {
                isEditable = true
                binding.stepSubmit.visibility = View.VISIBLE
            }
            CommonUtils.ACTION_TYPE_ADD -> {
                isEditable = true
                binding.stepSubmit.visibility = View.VISIBLE
            }
        }

        binding.interestedProjectName.isEnabled = isEditable
        binding.industryOfProject.isEnabled = isEditable
        binding.locationOfInterest.isEnabled = isEditable

        binding.spnrFinancialAssistance.isEnabled = isEditable

        binding.registrationFee.isEnabled = isEditable
        binding.franchisePlanningFor.isEnabled = isEditable
        binding.franchisePlanningAs.isEnabled = isEditable

        binding.spnrBusinessPlaceType.isEnabled = isEditable
        binding.businessPlaceSize.isEnabled = isEditable
    }

    private fun sendExpressionDetail() {
        val jsonObject = JsonObject()

        jsonObject.addProperty("project_name", binding.interestedProjectName.text.toString().trim())
        jsonObject.addProperty("industry", binding.industryOfProject.text.toString().trim())
      //  jsonObject.addProperty("location_interest", binding.locationOfInterest.text.toString().trim())
        jsonObject.addProperty("location_interest", binding.spnrState.selectedItem.toString().trim()+","+binding.spnrCity.selectedItem.toString().trim()+","+binding.spnrLocality.selectedItem.toString().trim())
        jsonObject.addProperty("financial_assistance", binding.spnrFinancialAssistance.selectedItem.toString().trim())
        jsonObject.addProperty("registration_fee", binding.registrationFee.text.toString().trim())
        jsonObject.addProperty("franchisee_planning_for", binding.franchisePlanningFor.text.toString().trim())
       // jsonObject.addProperty("franchisee_planning_for_other", binding.franchisePlanningAs.text.toString().trim())
        jsonObject.addProperty("franchisee_planning_as", binding.franchisePlanningAs.text.toString().trim())
        jsonObject.addProperty("business_place_type", binding.spnrBusinessPlaceType.selectedItem.toString().trim())
        jsonObject.addProperty("business_place_size", binding.businessPlaceSize.text.toString().trim())

        // Call remote Api service to save the Health Details
        val params: MutableMap<String, String> = HashMap()
        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition, actionType, uid)    }

}