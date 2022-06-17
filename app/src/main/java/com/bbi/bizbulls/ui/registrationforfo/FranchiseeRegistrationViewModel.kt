package com.bbi.bizbulls.ui.registrationforfo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bbi.bizbulls.R
import com.bbi.bizbulls.data.foregistration.steps.Data
import com.bbi.bizbulls.data.foregistration.steps.FoRegistrationSteps
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.Globals
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Daniel.
 */
class FranchiseeRegistrationViewModel : ViewModel() {
    var _selectedStepName = MutableLiveData<String>().apply {
        value = Globals.franchiseRegistration
    }
    val selectedStepName: LiveData<String> get() = _selectedStepName

    private var _allSteps = MutableLiveData<List<Data>>()
    val allSteps: LiveData<List<Data>> get() = _allSteps

    /**
     * Get Fo registration steps
     */
    fun getFoRegistrationSteps(context: Context) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }

        val call: Call<FoRegistrationSteps> =
            RetrofitClient.getUrl().foRegistrationSteps(sharedPrefsHelper.authToken)
        println("________URL ::${call.request().url}")
        println("________authToken ::${sharedPrefsHelper.authToken}")
        MyProcessDialog.showProgressBar(context, 0)
        call.enqueue(object : Callback<FoRegistrationSteps> {
            override
            fun onResponse(
                call: Call<FoRegistrationSteps>,
                responseObject: Response<FoRegistrationSteps>) {
                if (responseObject.isSuccessful) {
                    _allSteps.value = responseObject.body()?.data as ArrayList<Data>
                } else {
                    RetrofitClient.showResponseMessage(context, responseObject.code())
                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<FoRegistrationSteps>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(context, t)
            }
        })
    }


    /**
     * Post the details into server
     * 0 == Post Personal details
     * 1 == Health details
     * 2 == Expression of interest details
     * 3 == Check list details
     * 4 == Academic education details
     * 5 == Social Identity details
     * 6 == Bank details
     * 7 == Family details
     * 8 == Children details
     * 9 == Personal references details
     */

    fun sendDetailPostRequest(context: Context, params: MutableMap<String, String> = HashMap(), jsonObject: JsonObject, stepPosition: Any) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }
        var call: Call<ResponseBody>? = null
        when (stepPosition) {
            0 -> {
                call =
                    RetrofitClient.getUrl().personalDetailsPost(sharedPrefsHelper.authToken, params)
            }
            1 -> {
                call = RetrofitClient.getUrl()
                    .healthDetailsPost(sharedPrefsHelper.authToken, jsonObject)
            }
            2 -> {
                call = RetrofitClient.getUrl()
                    .expressionInterestDetailsPost(sharedPrefsHelper.authToken, jsonObject)
            }
            3 -> {
                call = RetrofitClient.getUrl()
                    .checkListDetailsPost(sharedPrefsHelper.authToken, jsonObject)
            }
            4 -> {
                call = RetrofitClient.getUrl()
                    .educationDetailsPost(sharedPrefsHelper.authToken, jsonObject)
            }
            5 -> {
                call = RetrofitClient.getUrl()
                    .socialIdentityDetailsPost(sharedPrefsHelper.authToken, jsonObject)
            }
            6 -> {
                call = RetrofitClient.getUrl()
                    .bankDetailsPost(sharedPrefsHelper.authToken, jsonObject)
            }
            7 -> {
                call = RetrofitClient.getUrl()
                    .familyDetailsPost(sharedPrefsHelper.authToken, jsonObject)
            }
            8 -> {
                call = RetrofitClient.getUrl()
                    .childrenDetailsPost(sharedPrefsHelper.authToken, jsonObject)
            }
            9 -> {
                call = RetrofitClient.getUrl()
                    .personalReferencesDetailsPost(sharedPrefsHelper.authToken, jsonObject)
            }
        }

        println("________URL ::${call?.request()?.url}")
        println("________Details $params")
        println("________Details $jsonObject")
        MyProcessDialog.showProgressBar(context, 0)
        call?.enqueue(object : Callback<ResponseBody> {
            override
            fun onResponse(
                call: Call<ResponseBody>,
                responseObject: Response<ResponseBody>) {
                if (responseObject.code() == 201) {
                   // sharedPrefsHelper.personalDetailID = responseObject.body()?.data?.id.toString()
                    responseSuccessMessage(context, stepPosition)
                } else {
                    RetrofitClient.showResponseMessage(context, responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(context, t)
            }
        })
    }

    private fun responseSuccessMessage(context: Context, position: Any){
        var message = ""
        when (position) {
            0 -> {
                message = context.resources.getString(R.string.personal_details)
            }
            1 -> {
                message = context.resources.getString(R.string.health_details)
            }
            2 -> {
                message = context.resources.getString(R.string.expression_details)
            }
            3 -> {
                message = context.resources.getString(R.string.checkList_details)
            }
            4 -> {
                message = context.resources.getString(R.string.education_details)
            }
            5 -> {
                message = context.resources.getString(R.string.socialIdentity_details)
            }
            6 -> {
                message = context.resources.getString(R.string.bank_details)
            }
            7 -> {
                message = context.resources.getString(R.string.family_details)
            }
            8 -> {
                message = context.resources.getString(R.string.children_details)
            }
            9 -> {
                message = context.resources.getString(R.string.personalReferences_details)
            }
        }
        FranchiseeRegistrationActivity.activityCalling(context,message)
    }
}