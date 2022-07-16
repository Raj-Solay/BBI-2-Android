package com.bbi.bizbulls

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bbi.bizbulls.databinding.ActivityOrderDetailBinding
import com.bbi.bizbulls.model.ApplyPromoResponse
import com.bbi.bizbulls.model.CashFreeTokenData
import com.bbi.bizbulls.model.StatusDataRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.Globals
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
import kotlin.random.Random

class OrderDetailActivity : AppCompatActivity(), CFCheckoutResponseCallback {
    private var subTotal: Int = 0
    private var netPayable: Int = 0
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@OrderDetailActivity) }
    private lateinit var binding: ActivityOrderDetailBinding
    lateinit var statusData: StatusDataRes
    private var isFranchiseeFee: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusData =intent!!.getSerializableExtra(Globals.REGISTRATION_FEES_DATA)!! as StatusDataRes
        binding.backNavigation.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        binding.btnCancel.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
        binding.apply {
            txtcustomerid.text = statusData?.number
            txtCustomerstatus.text = statusData?.customerStatus
            txtcustomername.text = statusData?.customerName
            txtcustomeraddress.text = statusData?.businessName
            txtcustomertime.text = statusData?.date
            if(isFranchiseeFee)
            {
                txtAmount.text = getString(R.string.rs).plus(statusData?.frenchiseeFee?.amount)
                txtRegFeeValue.text = getString(R.string.rs).plus(statusData?.frenchiseeFee?.amount)
                txtGstValue.text = getString(R.string.rs).plus(statusData?.frenchiseeFee?.gst)
                subTotal =
                    (statusData?.frenchiseeFee?.amount?.toInt()!! + statusData?.frenchiseeFee?.gst!!)
                txtSubTotalValue.text = getString(R.string.rs).plus(subTotal)
                txtSpecialDiscountValue.text =
                    getString(R.string.rs).plus(statusData?.frenchiseeFee?.discount?.toInt()!!)
                netPayable = subTotal - statusData?.frenchiseeFee?.discount?.toInt()!!
            }
            else{
                txtAmount.text = getString(R.string.rs).plus(statusData?.registrationFees?.amount)
                txtRegFeeValue.text = getString(R.string.rs).plus(statusData?.registrationFees?.amount)
                txtGstValue.text = getString(R.string.rs).plus(statusData?.registrationFees?.gst)
                subTotal =
                    (statusData?.registrationFees?.amount?.toInt()!! + statusData?.registrationFees?.gst!!)
                txtSubTotalValue.text = getString(R.string.rs).plus(subTotal)
                txtSpecialDiscountValue.text =
                    getString(R.string.rs).plus(statusData?.registrationFees?.discount?.toInt()!!)
                netPayable = subTotal - statusData?.registrationFees?.discount?.toInt()!!
            }

            txtNetAmountValue.text = getString(R.string.rs).plus(netPayable)
            btnSubmit.setOnClickListener {
                getPaymentToken(
                    netPayable
                )
            }
            btnApplyPromo.setOnClickListener {
                if(!TextUtils.isEmpty(etPromo.text.toString().trim()))
                {
                    applyPromo(etPromo.text.toString().trim(),netPayable)
                }
            }
        }
        try {
            // If you are using a fragment then you need to add this line inside onCreate() of your Fragment
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this)
        } catch (e: CFException) {
            e.printStackTrace()
        }


    }

    private fun getPaymentToken(amount: Int) {
        MyProcessDialog.showProgressBar(this@OrderDetailActivity, 0)
        val jsonObject = JsonObject()

        jsonObject.addProperty("order_amount", amount)
        jsonObject.addProperty("order_id", "OR"+Random(100000).nextInt())
        val email = statusData?.customerName

        var name = statusData?.customerName

        try {
            val split = email!!.split("@").toTypedArray()
            if(split.size > 1){
                name = split[0]
            }

        }catch (e : Exception){}


        jsonObject.addProperty("customer_name", name)
        jsonObject.addProperty("customer_email", sharedPrefsHelper.email)
        jsonObject.addProperty("customer_phone", sharedPrefsHelper.phone)
        jsonObject.addProperty("order_currency", "INR")
        jsonObject.addProperty("order_note", "some order note here")

        val call: Call<CashFreeTokenData> =
            RetrofitClient.getUrl().getPaymentToken(sharedPrefsHelper.authToken, jsonObject)
        call.enqueue(object : Callback<CashFreeTokenData> {
            override fun onResponse(
                call: Call<CashFreeTokenData>,
                response: Response<CashFreeTokenData>
            ) {
                if (response.isSuccessful) {
                    val cfSession = CFSession.CFSessionBuilder()
                        .setEnvironment(CFSession.Environment.PRODUCTION)
                        .setOrderToken(response.body()?.pg?.orderToken.toString())
                        .setOrderId(response.body()?.pg?.orderId.toString())
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
                     //   .setCFUIPaymentModes(cfPaymentComponent)
                        .setCFNativeCheckoutUITheme(cfTheme)
                        .build()

                    val gatewayService = CFPaymentGatewayService.getInstance()
                    gatewayService.doPayment(this@OrderDetailActivity, cfDropCheckoutPayment)

                } else {
                    CommonUtils.toast(
                        this@OrderDetailActivity,
                        this@OrderDetailActivity.resources.getString(R.string.something_wrong)
                    )
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
        updatePayment(orderID,"success")
    }

    override fun onPaymentFailure(cfErrorResponse: CFErrorResponse?, orderID: String?) {
        updatePayment(orderID,"failed")
        Toast.makeText(this@OrderDetailActivity, cfErrorResponse?.getMessage(), Toast.LENGTH_SHORT)
            .show()
    }

    private fun updatePayment(orderID: String?, status: String) {
        MyProcessDialog.showProgressBar(this@OrderDetailActivity, 0)
        val jsonObject = JsonObject()
        jsonObject.addProperty("order_id", orderID)
        jsonObject.addProperty("status", status)
        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().updatePayment(sharedPrefsHelper.authToken, jsonObject)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    setResult(RESULT_OK)
                    finish()
                } else {
                   /* CommonUtils.toast(
                        this@OrderDetailActivity,
                        this@OrderDetailActivity.resources.getString(R.string.something_wrong)
                    )*/
                }
                MyProcessDialog.dismiss()
                setResult(RESULT_OK)
                finish()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                setResult(RESULT_OK)
                finish()
              //  RetrofitClient.showFailedMessage(this@OrderDetailActivity, t)
            }
        })
    }

    private fun applyPromo(promo: String?, totalAmount: Int) {
        MyProcessDialog.showProgressBar(this@OrderDetailActivity, 0)
        val jsonObject = JsonObject()
        jsonObject.addProperty("code", promo)
        jsonObject.addProperty("totalAmount", totalAmount)
        val call: Call<ApplyPromoResponse> =
            RetrofitClient.getUrl().applyPromo(sharedPrefsHelper.authToken, jsonObject)
        call.enqueue(object : Callback<ApplyPromoResponse> {
            override fun onResponse(
                call: Call<ApplyPromoResponse>,
                response: Response<ApplyPromoResponse>
            ) {
                if (response.isSuccessful) {
                    if(isFranchiseeFee)
                    {
                        binding.txtSpecialDiscountValue.text =
                            getString(R.string.rs).plus(statusData?.frenchiseeFee?.discount?.plus(
                                response.body()?.discountAmt!!
                            ))
                        netPayable = subTotal - statusData?.frenchiseeFee?.discount!! - response.body()?.discountAmt!!
                    }
                    else{
                        binding.txtSpecialDiscountValue.text =
                            getString(R.string.rs).plus(statusData?.registrationFees?.discount?.plus(
                                response.body()?.discountAmt!!
                            ))
                        netPayable = subTotal - statusData?.registrationFees?.discount!! - response.body()?.discountAmt!!
                    }

                    binding.txtNetAmountValue.text = getString(R.string.rs).plus(netPayable)
                } else {
                    CommonUtils.toast(
                        this@OrderDetailActivity,
                        this@OrderDetailActivity.resources.getString(R.string.something_wrong)
                    )
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ApplyPromoResponse>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@OrderDetailActivity, t)
            }
        })
    }
}