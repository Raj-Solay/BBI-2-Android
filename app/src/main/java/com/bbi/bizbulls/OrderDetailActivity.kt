package com.bbi.bizbulls

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bbi.bizbulls.databinding.ActivityOrderDetailBinding
import com.bbi.bizbulls.databinding.FragmentStatusBinding
import com.bbi.bizbulls.model.CashFreeTokenData
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.cashfree.pg.api.CFPaymentGatewayService
import com.cashfree.pg.base.exception.CFException
import com.cashfree.pg.core.api.CFSession
import com.cashfree.pg.core.api.CFTheme
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback
import com.cashfree.pg.core.api.utils.CFErrorResponse
import com.cashfree.pg.ui.api.CFDropCheckoutPayment
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailActivity : AppCompatActivity(), CFCheckoutResponseCallback {
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@OrderDetailActivity) }
    private lateinit var binding: ActivityOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            // If you are using a fragment then you need to add this line inside onCreate() of your Fragment
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this)
        } catch (e: CFException) {
            e.printStackTrace()
        }
        binding.btnSubmit.setOnClickListener {
            getPaymentToken(
                it1.amount
            )
        }
    }
    private fun getPaymentToken(amount: Int) {
        MyProcessDialog.showProgressBar(this@OrderDetailActivity, 0)
        val jsonObject = JsonObject()
        jsonObject.addProperty("order_amount", amount)
        val call: Call<CashFreeTokenData> = RetrofitClient.getUrl().getPaymentToken( sharedPrefsHelper.authToken,jsonObject)
        call.enqueue(object : Callback<CashFreeTokenData> {
            override fun onResponse(
                call: Call<CashFreeTokenData>,
                response: Response<CashFreeTokenData>
            ) {
                if (response.code() == 200) {
                    val cfSession = CFSession.CFSessionBuilder()
                        .setEnvironment(CFSession.Environment.SANDBOX)
                        .setOrderToken(response.body()?.orderToken.toString())
                        .setOrderId(response.body()?.orderId.toString())
                        .build()

                    val cfTheme = CFTheme.CFThemeBuilder()
                        .setNavigationBarBackgroundColor("#6A3FD3")
                        .setNavigationBarTextColor("#FFFFFF")
                        .setButtonBackgroundColor("#6A3FD3")
                        .setButtonTextColor("#FFFFFF")
                        .setPrimaryTextColor("#000000")
                        .setSecondaryTextColor("#000000")
                        .build()
                    val cfDropCheckoutPayment = CFDropCheckoutPayment.CFDropCheckoutPaymentBuilder()
                        .setSession(cfSession)
//                        .setCFUIPaymentModes(cfPaymentComponent)
                        .setCFNativeCheckoutUITheme(cfTheme)
                        .build()

                    val gatewayService = CFPaymentGatewayService.getInstance()
                    gatewayService.doPayment(this@OrderDetailActivity, cfDropCheckoutPayment)

                } else {
                    CommonUtils.toast(this@OrderDetailActivity, this@OrderDetailActivity.resources.getString(R.string.something_wrong))
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<CashFreeTokenData>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@OrderDetailActivity, t)
            }
        })
    }
    override fun onPaymentVerify(orderID: String?) {
        //call api to update server
        updatePayment(orderID)
    }

    override fun onPaymentFailure(cfErrorResponse: CFErrorResponse?, orderID: String?) {
        Toast.makeText(this@OrderDetailActivity, cfErrorResponse?.getMessage(), Toast.LENGTH_SHORT).show()
    }

    private fun updatePayment(orderID: String?) {
        MyProcessDialog.showProgressBar(this@OrderDetailActivity, 0)
        val jsonObject = JsonObject()
        jsonObject.addProperty("orderId", orderID)
        val call: Call<ResponseBody> = RetrofitClient.getUrl().updatePayment( sharedPrefsHelper.authToken,jsonObject)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    showfinancialRelibility()
                } else {
                    CommonUtils.toast(this@OrderDetailActivity, this@OrderDetailActivity.resources.getString(R.string.something_wrong))
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@OrderDetailActivity, t)
            }
        })
    }
}