package com.biz.bizbulls

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.biz.bizbulls.databinding.ActivityFinabilityBinding
import com.biz.bizbulls.remote.RetrofitClient
import com.biz.bizbulls.sharedpref.SharedPrefsManager
import com.biz.bizbulls.utils.CommonUtils
import com.biz.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinabilityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinabilityBinding
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@FinabilityActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinabilityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backNavigation.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        binding.btncancelrefferance.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
        binding.apply {
            btnsubmitrefferance.setOnClickListener {
                if (TextUtils.isEmpty(etMonthlyIncome.text.toString().trim())) {
                    Toast.makeText(
                        this@FinabilityActivity,
                        "Please enter monthly income.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(
                        spnrFinancialAssistance.selectedItem.toString().trim()
                    )
                ) {
                    Toast.makeText(
                        this@FinabilityActivity,
                        "Please select income source.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    addFinability()
                }
            }
        }

    }

    private fun addFinability() {
        MyProcessDialog.showProgressBar(this@FinabilityActivity, 0)
        val jsonObject = JsonObject()
        jsonObject.addProperty("income", binding.etMonthlyIncome.text.toString().trim())
        jsonObject.addProperty("income_type", binding.spnrFinancialAssistance.selectedItem.toString().trim())
        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().addFinability(sharedPrefsHelper.authToken, jsonObject)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    setResult(RESULT_OK)
                    finish()
                } else {
                    CommonUtils.toast(
                        this@FinabilityActivity,
                        this@FinabilityActivity.resources.getString(R.string.something_wrong)
                    )
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@FinabilityActivity, t)
            }
        })
    }
}