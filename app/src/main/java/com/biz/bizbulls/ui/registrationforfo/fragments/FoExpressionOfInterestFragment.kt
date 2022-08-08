package com.biz.bizbulls.ui.registrationforfo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.biz.bizbulls.R
import com.biz.bizbulls.databinding.FoFrgExpressionOfInterestBinding
import com.biz.bizbulls.model.*
import com.biz.bizbulls.remote.RetrofitClient
import com.biz.bizbulls.sharedpref.SharedPrefsManager
import com.biz.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.biz.bizbulls.utils.CommonUtils
import com.biz.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import okhttp3.ResponseBody
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
    var stateListCode = arrayListOf<String>()
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
        stateListCode.add("Select Code")

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

      //  MyProcessDialog.showProgressBar(requireContext(), 0)
        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        val call = RetrofitClient.getUrl().getState(sharedPrefsHelper.authToken)

        var codeResList = resources.getStringArray(R.array.state_list_code)
        var i = 0
        codeResList.forEach {
            val itName = it.split("-")
            stateList.add(itName[0])
            stateListCode.add(itName[1])
            i++
            stateListId.add(""+i)

        }
        setStateAdapter()
       /* call?.enqueue(object : Callback<StateRes> {
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
        })*/
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
                    try{
                        getLocalityApi(cityListId.get(position+1).toString())
                    }catch (e  :Exception){}
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
        cityList.clear()
        cityList.add("Select City")
        cityListId.add("0")
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
        localityList.clear()
        localityList.add("Select Locality")
        localityListId.add("0")
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


        var statePos = binding.spnrState.selectedItemPosition
        var cityPos = binding.spnrCity.selectedItemPosition
        var localityPos = binding.spnrLocality.selectedItemPosition

        var cCode = ""

        var cnt = 0
        if(localityPos < 11){
            cCode = "0"+(localityPos)
        }else{
            cCode = ""+(localityPos)
        }

        var COMP_CODE = "BBIIND"+stateListCode.get(statePos)+""+cityPos+cCode

        CommonUtils.IS_FINISH_ACTIVITY = false
        CommonUtils.COMPANY_CODE = COMP_CODE
        Log.d("CompanyCode","Code : "+ COMP_CODE)

        getPersonalDetailAPi(COMP_CODE)

      }
    fun getPersonalDetailAPi(COMP_CODE: String) {
        MyProcessDialog.showProgressBar(requireContext(), 0)
        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        val call = RetrofitClient.getUrl().personalDetailsGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<PersonalDetailsViewRes> {
            override
            fun onResponse(
                call: Call<PersonalDetailsViewRes>,
                responseObject: Response<PersonalDetailsViewRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    if (responseObject.body()!!.data?.get(0)  != null) {
                        responseObject.body()!!.data?.get(0)?.let {
                            callPersonalAPi(it,COMP_CODE)
                        }
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<PersonalDetailsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })
    }

    fun callPersonalAPi(data: PersonalDetailsViewRes.Data, COMP_CODE: String) {
        val params: MutableMap<String, String> = HashMap()
        val jsonObject = JsonObject()
        jsonObject.addProperty("parent_company", CommonUtils.CURRENT_FORM_SUBMISSION)
        jsonObject.addProperty("regno", COMP_CODE)
        jsonObject.addProperty("fullname", data.fullname)
        jsonObject.addProperty("fathersname", data.fathersname)
        jsonObject.addProperty("mothersname", data.mothersname)
        jsonObject.addProperty("dob", data.dob)
        jsonObject.addProperty("age", data.age)
        jsonObject.addProperty("emailid", data.emailid)
        jsonObject.addProperty("gender", data.gender)
        jsonObject.addProperty("pob", data.pob)
        jsonObject.addProperty("mothertongue", data.mothertongue)
        var maritalstatus = data.maritalstatus

        jsonObject.addProperty("maritalstatus", maritalstatus)
        jsonObject.addProperty("alternatenumber", data.alternatenumber)
        jsonObject.addProperty("whatsappnumber", data.whatsappnumber)

        //permanent  address
        jsonObject.addProperty("permanent_add", data.permanentAdd)

        jsonObject.addProperty("permanent_acco_type", data.permanentAccoType)
        jsonObject.addProperty("permanent_acco_since", data.permanentAccoSince)
        jsonObject.addProperty("permanent_emergency_no", data.permanentEmergencyNo)


        //present address
        jsonObject.addProperty("present_add", data.presentAdd)
        jsonObject.addProperty("state", data.state)
        jsonObject.addProperty("zip", data.zip)
        var presentAccoType = data.permanentAccoType

        jsonObject.addProperty("present_acco_type", presentAccoType)
        jsonObject.addProperty("present_acco_since", data.presentAccoSince)
        jsonObject.addProperty("present_emergency_no", data.presentEmergencyNo)

        // Call remote Api service to save the Personal Details

        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        var call: Call<ResponseBody>? = null
        call =
            RetrofitClient.getUrl().personalDetailsPut(sharedPrefsHelper.authToken, jsonObject,data.id.toString())

        MyProcessDialog.showProgressBar(requireContext(), 0)
        call?.enqueue(object : Callback<ResponseBody> {
            override
            fun onResponse(
                call: Call<ResponseBody>,
                responseObject: Response<ResponseBody>) {
                if (responseObject.code() == 201 || responseObject.code() == 200) {
                    // sharedPrefsHelper.personalDetailID = responseObject.body()?.data?.id.toString()
                    val jsonObject = JsonObject()

                    jsonObject.addProperty("project_name", binding.interestedProjectName.text.toString().trim())
                    jsonObject.addProperty("industry", binding.industryOfProject.text.toString().trim())
                    //  jsonObject.addProperty("location_interest", binding.locationOfInterest.text.toString().trim())
                    jsonObject.addProperty("location_interest", binding.spnrState.selectedItem.toString().trim()+"," +
                            ""+binding.spnrCity.selectedItem.toString().trim()+"," +
                            ""+binding.spnrLocality.selectedItem.toString().trim())
                    jsonObject.addProperty("financial_assistance", binding.spnrFinancialAssistance.selectedItem.toString().trim())
                    jsonObject.addProperty("registration_fee", binding.registrationFee.text.toString().trim())
                    jsonObject.addProperty("franchisee_planning_for", binding.franchisePlanningFor.text.toString().trim())
                    // jsonObject.addProperty("franchisee_planning_for_other", binding.franchisePlanningAs.text.toString().trim())
                    jsonObject.addProperty("franchisee_planning_as", binding.franchisePlanningAs.text.toString().trim())
                    jsonObject.addProperty("business_place_type", binding.spnrBusinessPlaceType.selectedItem.toString().trim())
                    jsonObject.addProperty("business_place_size", binding.businessPlaceSize.text.toString().trim())

                    // Call remote Api service to save the Health Details
                    val params: MutableMap<String, String> = HashMap()
                    FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), params, jsonObject, stepPosition, actionType, uid)

                } else {
                //    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
            //    RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }

}