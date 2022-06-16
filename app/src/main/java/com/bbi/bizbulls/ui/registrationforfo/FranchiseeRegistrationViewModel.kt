package com.bbi.bizbulls.ui.registrationforfo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bbi.bizbulls.R
import com.bbi.bizbulls.data.expression.ExpressionOfInterest
import com.bbi.bizbulls.data.foregistration.steps.Data
import com.bbi.bizbulls.data.foregistration.steps.FoRegistrationSteps
import com.bbi.bizbulls.data.franchiseregresponse.personaldetailscreate.PersonalDetailsSaveResponse
import com.bbi.bizbulls.data.health.HealthDetailsSaveResponse
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
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
     * Post Personal details
     */
    fun sendPersonalDetail(context: Context, params: MutableMap<String, String> = HashMap()) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }

        val call: Call<PersonalDetailsSaveResponse> =
            RetrofitClient.getUrl().personalDetailsPost(sharedPrefsHelper.authToken, params)
        println("________URL ::${call.request().url}")
        println("________Details $params")
        MyProcessDialog.showProgressBar(context, 0)
        call.enqueue(object : Callback<PersonalDetailsSaveResponse> {
            override
            fun onResponse(
                call: Call<PersonalDetailsSaveResponse>,
                responseObject: Response<PersonalDetailsSaveResponse>) {
                if (responseObject.code() == 201) {
                    sharedPrefsHelper.personalDetailID = responseObject.body()?.data?.id.toString()
                    FranchiseeRegistrationActivity.activityCalling(
                        context,
                        context.resources.getString(R.string.personal_details)
                    )
                } else {
                    RetrofitClient.showResponseMessage(context, responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<PersonalDetailsSaveResponse>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(context, t)
            }
        })
    }

    /**
     * Post Health details
     */
    fun sendHealthDetail(context: Context, jsonObject: JsonObject) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }

        val call: Call<HealthDetailsSaveResponse> =
            RetrofitClient.getUrl().healthDetailsPost(sharedPrefsHelper.authToken, jsonObject)
        println("________URL ::${call.request().url}")
        println("________Details $jsonObject")
        MyProcessDialog.showProgressBar(context, 0)
        call.enqueue(object : Callback<HealthDetailsSaveResponse> {
            override
            fun onResponse(
                call: Call<HealthDetailsSaveResponse>,
                responseObject: Response<HealthDetailsSaveResponse>
            ) {
                if (responseObject.code() == 201) {
                    FranchiseeRegistrationActivity.activityCalling(
                        context,
                        context.resources.getString(R.string.health_details)
                    )
                } else {
                    RetrofitClient.showResponseMessage(context, responseObject.code())
                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<HealthDetailsSaveResponse>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(context, t)
            }
        })
    }

    /**
     * Post expression of interest detail
     */
    fun sendExpressionOfInterestDetail(context: Context, jsonObject: JsonObject) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }

        val call: Call<ExpressionOfInterest> =
            RetrofitClient.getUrl().expressionInterestDetailsPost(sharedPrefsHelper.authToken, jsonObject)
        println("________URL ::${call.request().url}")
        println("________Details $jsonObject")
        MyProcessDialog.showProgressBar(context, 0)
        call.enqueue(object : Callback<ExpressionOfInterest> {
            override
            fun onResponse(
                call: Call<ExpressionOfInterest>,
                responseObject: Response<ExpressionOfInterest>
            ) {
                if (responseObject.code() == 201) {
                    FranchiseeRegistrationActivity.activityCalling(
                        context,
                        context.resources.getString(R.string.expression_details)
                    )
                } else {
                    RetrofitClient.showResponseMessage(context, responseObject.code())
                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<ExpressionOfInterest>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(context, t)
            }
        })
    }

    /**
     * Post check list detail
     */
    fun sendCheckListDetail(context: Context, jsonObject: JsonObject) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }

        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().checkListDetailsPost(sharedPrefsHelper.authToken, jsonObject)
        println("________URL ::${call.request().url}")
        println("________Details $jsonObject")
        MyProcessDialog.showProgressBar(context, 0)
        call.enqueue(object : Callback<ResponseBody> {
            override
            fun onResponse(
                call: Call<ResponseBody>,
                responseObject: Response<ResponseBody>
            ) {
                if (responseObject.code() == 201) {
                    FranchiseeRegistrationActivity.activityCalling(
                        context,
                        context.resources.getString(R.string.checkList_details)
                    )
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
}